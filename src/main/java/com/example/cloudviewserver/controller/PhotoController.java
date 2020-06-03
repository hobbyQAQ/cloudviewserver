package com.example.cloudviewserver.controller;

import com.example.cloudviewserver.entity.*;
import com.example.cloudviewserver.entity.domain.AddFaceResult;
import com.example.cloudviewserver.entity.domain.DetectResult;
import com.example.cloudviewserver.entity.domain.FaceListResult;
import com.example.cloudviewserver.entity.domain.MatchResult;
import com.example.cloudviewserver.service.CharaterService;
import com.example.cloudviewserver.service.FaceService;
import com.example.cloudviewserver.service.FacemapperService;
import com.example.cloudviewserver.service.PhotoService;
import com.example.cloudviewserver.utils.PhotoUtil;
import com.example.cloudviewserver.utils.Result;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
// TODO: 2020/5/24 文件存储问题 到底存在哪个目录下  target 还是 webapp
/**
 * (Photo)表控制层
 *
 * @author makejava
 * @since 2020-04-20 13:51:20
 */
@RestController
@RequestMapping("photo")
public class PhotoController {

    @Value("${web.upload-path}")
    private String baseUploadPath;

    /**
     * 服务对象
     */
    @Resource
    private PhotoService photoService;

    @Resource
    private FaceService faceService;

    @Resource
    private CharaterService charaterService;

    @Resource
    private FacemapperService facemapperService;
    private Photo newPhoto;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Photo selectOne(Integer id) {
        return this.photoService.queryById(id);
    }

    @GetMapping("get/{uid}")
    public Result<List<Photo>> getUserAllPhotoByUserId(@PathVariable(value = "uid") Integer uid) {
        return photoService.getPhotoByUid(uid);
    }

    /**
     * 根据用户id获取用户照片
     * @return
     */
    @GetMapping("get")
    public Result<List<Photo>> getPhoto(HttpSession session) {
        int uid = 1;
        return photoService.getPhotoByUid(uid);
    }

    /**
     * 文件下载
     * @param pid 照片id
     * @return
     * @throws FileNotFoundException
     */
    @GetMapping("download")
    public Result download(@RequestParam("pid") Integer pid, HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {

        Photo photo = photoService.queryById(pid);
        String realPath = baseUploadPath + photo.getPath();
        FileInputStream is = new FileInputStream(new File(realPath));
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(PhotoUtil.Path2Name(photo.getPath(),1),"UTF-8"));
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            IOUtils.copy(is,os);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("下载失败");
        }finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
        return Result.success("下载成功");
    }

    /**
     * 单文件上传
     *
     * @param file
     * @param uid
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("uid") Integer uid) throws IOException {
        if (file.isEmpty()) {
            return Result.fail("上传失败");
        } else {
            //获得原始文件名
            String originalFilename = file.getOriginalFilename();
            if (FilenameUtils.isExtension(originalFilename, new String[]{"jpeg", "jpg", "png"})) {
                //以用户id命名的路径
                String path = baseUploadPath + "photo/"+ uid;
                System.out.println("存储路径："+ path);
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                if (PhotoUtil.isMatchedFilename(originalFilename)) {
                    //文件格式已经匹配了，直接存到对应用户id命名的文件夹内
                    File dest = new File(path, originalFilename);
                    file.transferTo(dest);
                    //上传成功后把数据保存到数据库
                    if (updateDatabase(dest.getAbsolutePath(), uid)) {
                        return Result.success("上传文件成功,文件路径为：" + dest.getAbsolutePath());
                    }else {
                        return Result.fail("上传失败");
                    }
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_HHmmss");
                    //给文件已规定好的日期格式重新命名
                    String fileNewName = "IMG_"
                            + sdf.format(new Date())
                            + "."
                            + FilenameUtils.getExtension(originalFilename);
                    File dest = new File(path, fileNewName);
                    file.transferTo(dest);
                    //上传成功后把数据保存到数据库
                    if (updateDatabase(dest.getAbsolutePath(), uid)) {
                        return Result.success("上传文件成功,文件路径为：" + dest.getAbsolutePath());
                    }else {
                        return Result.fail("上传失败");
                    }

                }
            } else {
                return Result.fail("不支持的文件类型");
            }
            //判断文件后缀
        }
    }


    /**
     * 多文件上传
     * @param files
     * @param session
     * @return
     */
    @PostMapping("uploads")
    private Result uploads(@RequestParam("files")List<MultipartFile> files,@RequestParam("uid") Integer uid,HttpSession session) throws IOException {
        if (files.size() == 0) {
            return Result.fail("文件为空");
        }
        int picNum = 0;
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String originalFilename = file.getOriginalFilename();
            if (FilenameUtils.isExtension(originalFilename, new String[]{"jpeg", "jpg", "png"})) {
                //以用户id命名的路径
                String path = baseUploadPath + "photo/" + uid;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                if (PhotoUtil.isMatchedFilename(originalFilename)) {
                    //文件格式已经匹配了，直接存到对应用户id命名的文件夹内
                    File dest = new File(path, originalFilename);
                    file.transferTo(dest);
                    //上传成功后把数据保存到数据库
                    if (updateDatabase(dest.getAbsolutePath(), uid)) {
                        picNum ++;
                    }
                } else {
                    //给文件已规定好的日期格式重新命名
                    SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_HHmmss");
                    // TODO: 2020/5/31 有问题
                    String fileNewName = "IMG_"
                            + sdf.format(new Date())
                            + "."
                            + FilenameUtils.getExtension(originalFilename);
                    File dest = new File(path, fileNewName);
                    file.transferTo(dest);
                    //上传成功后把数据保存到数据库
                    if (updateDatabase(dest.getAbsolutePath(), uid)) {
                        picNum ++;
                    }
                }
            } else {
                return Result.fail("含有不支持的文件类型");
            }
        }
        return Result.success("成功上传"+picNum+"张照片");
    }

    /**
     * 删除照片
     * @param pid 照片的id
     * @param session 获取用户信息
     * @return
     */
    @GetMapping("delete")
    private Result delete(@RequestParam("pid")Integer pid,@RequestParam("uid") Integer uid, HttpSession session){
        Photo photo = photoService.queryById(pid);
        boolean b = photoService.deleteById(pid);
        String path = null;
        try {
            path = baseUploadPath + photo.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(path);
        boolean delete = file.delete();
        if (photo.getType() == 1) {
            Face face = faceService.queryByPid(pid);
            faceService.deleteByPid(pid);
            facemapperService.deleteByFaceToken(face.getFaceToken());
        }
        return Result.success("删除成功");
    }

    /**
     * 通过人脸获取相片
     * @param cid
     * @param session
     * @return
     */
    @GetMapping("get/by/face")
    private Result getPhotoByCharater(@RequestParam("cid") Integer cid,HttpSession session) {
        int uid = 1;
        return  photoService.getPhotoByCid(cid, uid);
    }

    @GetMapping("get/scenery")
    private Result getScenery(HttpSession session) {
        int uid = 1;
        return  Result.success(photoService.getPhotoByType0());
    }

    /**
     * 上传图片更新数据库、更新人脸库、更新人脸图片
     * @param absolutePath
     * @param id
     * @return
     */
    private boolean updateDatabase(String absolutePath, Integer id) {
        //更新photo、photomapper 、face数据库

        DetectResult detectResult = faceService.faceDetect(absolutePath);
        Photo photo = null;
        if (detectResult.getError_code()==0) {
            //成功
            photo = new Photo();
            photo.setPath(PhotoUtil.getPath(absolutePath));
            photo.setType(1);
            newPhoto = photoService.insert(photo);
            List<DetectResult.ResultBean.FaceListBean> face_list = detectResult.getResult().getFace_list();
            for (int j = 0; j < face_list.size(); j++) {
                DetectResult.ResultBean.FaceListBean faceListBean = face_list.get(j);
                System.out.println("face_token"+j+"==="+ faceListBean.getFace_token());
                Face face = new Face();
                face.setFaceToken(faceListBean.getFace_token());
                face.setHeight((double)faceListBean.getLocation().getHeight());
                face.setWidth((double)faceListBean.getLocation().getWidth());
                face.setLeft((double)faceListBean.getLocation().getLeft());
                face.setTop((double)faceListBean.getLocation().getTop());
                face.setPid(newPhoto.getId());
                face.setRotation(faceListBean.getLocation().getRotation());
                //图像文件过大，进行压缩 ,
                // 目前采用android端压缩
                try {
                    Thumbnails.of(absolutePath)
                            .scale(1f)
                            .outputQuality(0.5f)
                            .toFile(absolutePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将人脸图片裁剪下来并保存
                BufferedImage srcBfi = PhotoUtil.file2img(absolutePath);
                BufferedImage bufferedImage = PhotoUtil.img_tailor(srcBfi
                        ,face.getLeft().intValue()*19/20
                        ,face.getTop().intValue()*19/20
                        ,face.getWidth().intValue()*5/2
                        ,face.getHeight().intValue()*5/2);
                if (face.getRotation() < -80) {
                    bufferedImage = PhotoUtil.img_tailor(srcBfi
                            ,face.getLeft().intValue()*19/20
                            ,face.getTop().intValue()*19/20
                            ,face.getWidth().intValue()*5/2
                            ,face.getHeight().intValue()*5/2);
//                    bufferedImage = PhotoUtil.img_rotation(bufferedImage, 90);
                }else if(face.getRotation() > 80){
                    bufferedImage = PhotoUtil.img_rotation(bufferedImage, -90);
                }

                String faceToken = face.getFaceToken();
                String destPath = baseUploadPath+"face/"+id+"/"+faceToken+".jpg";
                face.setPath(PhotoUtil.getFacePath(destPath));
                PhotoUtil.img2file(bufferedImage,"jpg",destPath);
                faceService.insert(face);

                FaceListResult faceListFromBaidu = faceService.getFaceListFromBaidu();
                List<FaceListResult.ResultBean.FaceListBean> face_list_match = faceListFromBaidu.getResult().getFace_list();
                if (faceListFromBaidu.getResult().getFace_list() != null) {

                }
                if (face_list_match.size() == 0){
                    Charater charater = new Charater();
                    charater = charaterService.insert(charater);
                    facemapperService.insert(new Facemapper(charater.getId(),face.getFaceToken()));
                }
                int theSameFace = -1;
                for (int i = 0; i < face_list_match.size(); i++) {
                    // match face
                    MatchResult matchResult = faceService.matchFace(face.getFaceToken(), face_list_match.get(i).getFace_token());

                    if (matchResult.getScore() >= 80) {
                        theSameFace = i;
                        break;
                    }
                }
                if (theSameFace<0){
                    //如果人脸库中没有相同人脸，则新建一个charater，把charater的id和face的facetoken记录到faceMapper中
                    Charater charater = new Charater();
                    charater = charaterService.insert(charater);
                    charater.setName("未命名"+charater.getId());
                    charater = charaterService.update(charater);
                    facemapperService.insert(new Facemapper(charater.getId(), face.getFaceToken()));
                }else{
                    //如果有匹配的人脸，则把人脸和相同人脸的人物记录到faceMapper
                    Facemapper facemapper = facemapperService.queryByFaceToken(face_list_match.get(theSameFace).getFace_token());
                    if (facemapper != null) {
                        Facemapper newFacemapper = new Facemapper(facemapper.getCid(),face.getFaceToken());
                        facemapperService.insert(newFacemapper);
                    }else{
                        System.err.println("查询失败，映射表中没有该人脸");
                    }
                }
                //将人脸上传至人脸库
                AddFaceResult addFaceResult = faceService.faceAdd2Baidu(absolutePath, 1, 1);
            }
        }else{
            photo = new Photo();
            photo.setPath(PhotoUtil.getPath(absolutePath));
            photo.setType(0);
            newPhoto = photoService.insert(photo);
        }
        Photomapper photomapper = new Photomapper(newPhoto.getId(), id);
        photoService.insertPhotomapper(photomapper);
        return true;
    }
}