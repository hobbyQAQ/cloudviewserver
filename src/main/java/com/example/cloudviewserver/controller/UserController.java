package com.example.cloudviewserver.controller;

import com.example.cloudviewserver.entity.Photo;
import com.example.cloudviewserver.entity.User;
import com.example.cloudviewserver.service.PhotoService;
import com.example.cloudviewserver.service.UserService;
import com.example.cloudviewserver.utils.PhotoUtil;
import com.example.cloudviewserver.utils.Result;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-04-20 13:51:20
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @Resource
    private PhotoService photoService;

    /**
     * 通过主键查询单条数据
     *
     * @param uid 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{uid}")
    public User selectOne(@PathVariable("uid") Integer uid) {
        return this.userService.queryById(uid);
    }


    @GetMapping("hello")
    public String hello(){
        return "hello cloudview";
    }

    @PostMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.fail("上传失败");
        } else {
            //获得原始文件名
            String originalFilename = file.getOriginalFilename();
            if (FilenameUtils.isExtension(originalFilename, new String[]{"jpeg", "jpg", "png"})) {
                //以用户id命名的路径
                String path = "D://yunyin/upload/photo/" + 3;
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
                    return Result.fail("上传文件成功,文件路径为：" + dest.getAbsolutePath());

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
                    return Result.success("上传文件成功,文件路径为：" + dest.getAbsolutePath());

                }
            } else {
                return Result.fail("不支持的文件类型");
            }
            //判断文件后缀
        }
    }

}