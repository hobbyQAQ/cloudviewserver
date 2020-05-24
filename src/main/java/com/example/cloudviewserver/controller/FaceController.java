package com.example.cloudviewserver.controller;

import com.example.cloudviewserver.entity.Charater;
import com.example.cloudviewserver.entity.Face;
import com.example.cloudviewserver.entity.Facemapper;
import com.example.cloudviewserver.service.CharaterService;
import com.example.cloudviewserver.service.FaceService;
import com.example.cloudviewserver.service.FacemapperService;
import com.example.cloudviewserver.utils.Result;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @Resource
    private CharaterService charaterService;

    @Resource
    private FacemapperService facemapperService;


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
     * @return
     */
    @GetMapping("get/all")
    public Result getAllFace(HttpSession session){
        // TODO: 2020/5/4 从session获取用户id
        int uid = 1;
        List<Face> faces = faceService.queryAll();
        return Result.success(faces);
    }



    /**
     * 用户更新脸的名字
     * @param faceName
     * @return
     */
    @GetMapping("update/name")
    public Result updateFaceName(@RequestParam("faceName") String faceName, @RequestParam("faceToken") String faceToken){
        // TODO: 2020/5/4 从session获取用户id
        Result<Face> faceResult = faceService.queryById(faceToken);
        Face data = faceResult.getData();

        Facemapper facemapper = facemapperService.queryByFaceToken(faceToken);
        Charater charater = charaterService.queryById(facemapper.getCid());
        charater.setName(faceName);
        charaterService.update(charater);
        return Result.success("更新成功");
    }


}