package com.example.cloudviewserver.dao;

import com.example.cloudviewserver.entity.Charater;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Charater)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-05 20:56:45
 */
@Mapper
public interface CharaterDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Charater queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Charater> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
    List<Charater> getAll();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param charater 实例对象
     * @return 对象列表
     */
    List<Charater> queryAll(Charater charater);

    /**
     * 新增数据
     *
     * @param charater 实例对象
     * @return 影响行数
     */
    int insert(Charater charater);

    /**
     * 修改数据
     *
     * @param charater 实例对象
     * @return 影响行数
     */
    int update(Charater charater);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}