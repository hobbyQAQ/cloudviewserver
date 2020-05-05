package com.example.cloudviewserver.controller;

import com.example.cloudviewserver.entity.Face;
import com.example.cloudviewserver.service.FaceService;
import com.example.cloudviewserver.utils.Result;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (Face)表控制层
 *
 * @author makejava
 * @since 2020-05-03 18:48:54
 */
@RestController
@RequestMapping("face")
public class FaceController {
    /**
     * 服务对象
     */
    @Resource
    private FaceService faceService;

    /**
     * 通过主键查询单条数据
     * @param face_token 主键
     * @return 单条数据
     */
    @GetMapping("get")
    public Result getFace(@RequestParam("face_token") String face_token) {
        return this.faceService.queryById(face_token);
    }

    /**
     * 根据用户id获取该用户云相册的所有脸
     * @param uid
     * @return
     */
    @GetMapping("get/all")
    public Result getAllFace(@RequestParam("uid")Integer uid){
        // TODO: 2020/5/4 从session获取用户id
        return Result.success("get all faces by uid");
    }

    /**
     * 用户更新脸的名字
     * @param faceName
     * @return
     */
    @GetMapping("update/name")
    public Result updateFaceName(@RequestParam("faceName") String faceName, HttpSession session){
        // TODO: 2020/5/4 从session获取用户id
        return Result.success("更新成功");
    }


}