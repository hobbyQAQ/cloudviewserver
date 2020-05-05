package com.example.cloudviewserver.dao;

import com.example.cloudviewserver.entity.Facemapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Facemapper)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-05 20:56:46
 */
@Mapper
public interface FacemapperDao {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    Facemapper queryByFaceToken(@Param("faceToken")String faceToken);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Facemapper> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param facemapper 实例对象
     * @return 对象列表
     */
    List<Facemapper> queryAll(Facemapper facemapper);

    /**
     * 新增数据
     *
     * @param facemapper 实例对象
     * @return 影响行数
     */
    int insert(Facemapper facemapper);

    /**
     * 修改数据
     *
     * @param facemapper 实例对象
     * @return 影响行数
     */
    int update(Facemapper facemapper);

    /**
     * 通过主键删除数据
     *
     * @param
     * @return 影响行数
     */
    int deleteByFaceToken(@Param("faceToken") String faceToken);

}