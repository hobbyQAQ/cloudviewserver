package com.example.cloudviewserver.service;

import com.example.cloudviewserver.entity.Face;
import com.example.cloudviewserver.entity.domain.DetectResult;
import com.example.cloudviewserver.entity.domain.FaceListResult;
import com.example.cloudviewserver.entity.domain.MatchResult;
import com.example.cloudviewserver.utils.Result;

import java.util.List;

/**
 * (Face)表服务接口
 *
 * @author makejava
 * @since 2020-05-03 18:48:54
 */
public interface FaceService {

    /**
     * 通过ID查询单条数据
     *
     * @param faceToken 主键
     * @return 实例对象
     */
    Result<Face> queryById(String faceToken);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Face> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param face 实例对象
     * @return 实例对象
     */
    Face insert(Face face);

    /**
     * 修改数据
     *
     * @param face 实例对象
     * @return 实例对象
     */
    Result update(Face face);

    /**
     * 通过主键删除数据
     *
     * @param faceToken 主键
     * @return 是否成功
     */
    boolean deleteById(String faceToken);

    /**
     * 探测人脸
     * @param filePath
     * @return 探测结果
     */
    DetectResult faceDetect(String filePath);

    /**
     * 添加到人脸库
     * @param filePath
     * @return 探测结果
     */
    String faceAdd2Baidu(String filePath , Integer uid, Integer cid);

    /**
     * 更新到人脸库
     * @param filePath
     * @return 探测结果
     */
    String faceUpdate2Baidu(String filePath , Integer uid, Integer cid);

    /**
     * 从人脸库
     * @return
     */
    FaceListResult getFaceListFromBaidu();

    MatchResult matchFace(String faceToken, String face_token);

    boolean deleteByPid(Integer pid);

    Face queryByPid(Integer pid);

    List<Face> queryAll();
}