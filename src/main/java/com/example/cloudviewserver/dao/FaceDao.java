package com.example.cloudviewserver.dao;

import com.example.cloudviewserver.entity.Face;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Face)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-03 18:48:54
 */
@Mapper
public interface FaceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param faceToken 主键
     * @return 实例对象
     */
    Face queryById(String faceToken);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Face> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     *
     * @return 对象列表
     */
    List<Face> queryAll();

    /**
     * 新增数据
     *
     * @param face 实例对象
     * @return 影响行数
     */
    int insert(Face face);

    /**
     * 修改数据
     *
     * @param face 实例对象
     * @return 影响行数
     */
    int update(Face face);

    /**
     * 通过主键删除数据
     *
     * @param faceToken 主键
     * @return 影响行数
     */
    int deleteById(String faceToken);

    int deleteByPid(Integer pid);

    Face queryByPid(Integer pid);

    List<Face> getFacelistByCid(int cid);
}