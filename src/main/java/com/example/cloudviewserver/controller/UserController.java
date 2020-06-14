package com.example.cloudviewserver.controller;

import com.example.cloudviewserver.entity.Photo;
import com.example.cloudviewserver.entity.User;
import com.example.cloudviewserver.service.PhotoService;
import com.example.cloudviewserver.service.UserService;
import com.example.cloudviewserver.utils.PhotoUtil;
import com.example.cloudviewserver.utils.Result;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${web.upload-path}")
    private String baseUploadPath;

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


    @GetMapping("login")
    public Result login(@RequestParam("account") String account,@RequestParam("password") String password){
        User user = userService.query(account);
        if(user.getAccount().equals(account) && user.getPassword().equals(password)){
            return Result.success(user);
        }
        return Result.fail("登录失败");
    }

    @GetMapping("update")
    public Result update(@RequestParam("uid") Integer uid,@RequestParam("nickname") String nickname){
        User user = userService.queryById(uid);
        user.setNickname(nickname);
        user = userService.update(user);
        return Result.success(user);
    }





    @PostMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("uid") Integer uid) throws IOException {
        if (file.isEmpty()) {
            return Result.fail("上传失败");
        } else {
            //获得原始文件名
            String originalFilename = file.getOriginalFilename();
            if (FilenameUtils.isExtension(originalFilename, new String[]{"jpeg", "jpg", "png"})) {
                //以用户id命名的路径
                String path = baseUploadPath + "cover/" + uid;
                System.out.println("存储路径：" + path);
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File dest = new File(path, originalFilename);
                file.transferTo(dest);
                //上传成功后把数据保存到数据库
                User user = userService.queryById(uid);
                user.setCoverPath(PhotoUtil.getCoverPath(dest.getAbsolutePath()));
                user = userService.update(user);
                return Result.success(user);
            }
        }
        return Result.fail("上传失败");
    }

}