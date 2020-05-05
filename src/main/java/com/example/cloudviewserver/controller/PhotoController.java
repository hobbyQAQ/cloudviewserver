package com.example.cloudviewserver.controller;

import com.example.cloudviewserver.entity.*;
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
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (Photo)表控制层
 *
 * @author makejava
 * @since 2020-04-20 13:51:20
 */
@RestController
@RequestMapping("photo")
public class PhotoController {

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

    @GetMapping("get")
    public Result<List<Photo>> getPhoto(@RequestParam("user") Integer uid) {
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
        String realPath = ResourceUtils.getURL("classpath:").getPath() + photo.getPath();
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
                String path = ResourceUtils.getURL("classpath:").getPath() + "/static/" + uid;
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
                    }

                }
            } else {
                return Result.fail("不支持的文件类型");
            }
            //判断文件后缀
        }
        return Result.fail("上传失败");
    }


    /**
     *
     * @param files
     * @param uid
     * @return
     */
    @PostMapping("uploads")
    private Result uploads(@RequestParam("files")List<MultipartFile> files,@RequestParam("uid")Integer uid) throws IOException {
        if (files.size() == 0) {
            return Result.fail("文件为空");
        }
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String originalFilename = file.getOriginalFilename();
            if (FilenameUtils.isExtension(originalFilename, new String[]{"jpeg", "jpg", "png"})) {
                //以用户id命名的路径
                String path = ResourceUtils.getURL("classpath:").getPath() + "/static/" + uid;
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
                    }
                } else {
                    //给文件已规定好的日期格式重新命名
                    String fileNewName = "IMG_"
                            + new SimpleDateFormat("YYYYMMdd_HHmmss")
                            + "."
                            + FilenameUtils.getExtension(originalFilename);
                    File dest = new File(path, fileNewName);
                    file.transferTo(dest);
                    //上传成功后把数据保存到数据库
                    if (updateDatabase(dest.getAbsolutePath(), uid)) {
                        return Result.success("上传文件成功,文件路径为：" + dest.getAbsolutePath());
                    }
                }
            } else {
                return Result.fail("不支持的文件类型");
            }
        }
        return Result.fail("上传失败");
    }


    /**
     * 上传图片更新数据库
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
                face.setPid(photo.getId());
                face.setRotation(faceListBean.getLocation().getRotation());
                faceService.insert(face);
                //图像文件过大，进行压缩
                try {
                    Thumbnails.of(absolutePath)
                            .scale(1f)
                            .outputQuality(0.8f)
                            .toFile(absolutePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将人脸图片裁剪下来并保存
                BufferedImage srcBfi = PhotoUtil.file2img(absolutePath);
                BufferedImage bufferedImage = PhotoUtil.img_tailor(srcBfi
                    ,face.getLeft().intValue()
                    ,face.getTop().intValue()
                    ,face.getWidth().intValue()
                    ,face.getHeight().intValue());
                //如果有旋转角，将脸旋转回来
                if (face.getRotation() < -80) {
                    srcBfi = PhotoUtil.img_rotation(srcBfi, 90);
                }
                String faceToken = face.getFaceToken();
                String destPath = "D:/cloudviewserver/target/classes/face/1/"+faceToken+".jpg";
                PhotoUtil.img2file(srcBfi,"jpg",destPath);
                //将人脸上传至人脸库
                FaceListResult faceListFromBaidu = faceService.getFaceListFromBaidu();
                List<FaceListResult.FaceListBean> face_list1 = faceListFromBaidu.getFace_list();
                if (face_list1.size() == 0){
                    Charater charater = new Charater();
                    charater = charaterService.insert(charater);
                    facemapperService.insert(new Facemapper(charater.getId(),face.getFaceToken()));
                }
                for (int i = 0; i < face_list1.size(); i++) {
                    // TODO: 2020/5/5 match face
                    MatchResult matchResult = faceService.matchFace(face.getFaceToken(), face_list1.get(i).getFace_token());

                    if (matchResult.getScore() >= 80) {
                        //如果有匹配的人脸，则把人脸和相同人脸的人物记录到faceMapper
                        Facemapper facemapper = facemapperService.queryByFaceToken(face_list1.get(i).getFace_token());
                        if (facemapper != null) {
                            Facemapper facemapper1 = new Facemapper(facemapper.getCid(),face.getFaceToken());
                        }else{
                            System.err.println("查询失败，映射表中没有该人脸");
                        }
                    }else{
                        //如果人脸库中没有相同人脸，则新建一个charater，把charater的id和face的facetoken记录到faceMapper中
                        Charater charater = new Charater();
                        charater = charaterService.insert(charater);
                        facemapperService.insert(new Facemapper(charater.getId(), face.getFaceToken()));
                    }
                }


            }
        }else{
            photo = new Photo();
            photo.setPath(PhotoUtil.getPath(absolutePath));
            photo.setType(0);
        }
        Photo insert = photoService.insert(photo);
        Photomapper photomapper = new Photomapper(insert.getId(), id);
        photoService.insertPhotomapper(photomapper);
        return true;
    }
}