package com.example.cloudviewserver.controller;

import com.example.cloudviewserver.entity.Photo;
import com.example.cloudviewserver.entity.Photomapper;
import com.example.cloudviewserver.service.PhotoService;
import com.example.cloudviewserver.utils.PhotoUtil;
import com.example.cloudviewserver.utils.Result;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

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
                    if (uploadDatabase(dest.getAbsolutePath(), uid)) {
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

                    if (uploadDatabase(dest.getAbsolutePath(), uid)) {
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
                    if (uploadDatabase(dest.getAbsolutePath(), uid)) {
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

                    if (uploadDatabase(dest.getAbsolutePath(), uid)) {
                        return Result.success("上传文件成功,文件路径为：" + dest.getAbsolutePath());
                    }
                }
            } else {
                return Result.fail("不支持的文件类型");
            }
        }
        return Result.fail("上传失败");
    }


    private boolean uploadDatabase(String absolutePath, Integer id) {
        //更新photo、和photomapper数据库
        //弄一个service
        Photo photo = new Photo();
        photo.setPath(PhotoUtil.getPath(absolutePath));
        Photo insert = photoService.insert(photo);
        Photomapper photomapper = new Photomapper(insert.getId(), id);
        photoService.insertPhotomapper(photomapper);
        return true;
    }
}