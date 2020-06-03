package com.example.cloudviewserver.service;

import com.example.cloudviewserver.entity.Charater;
import java.util.List;

/**
 * (Charater)表服务接口
 *
 * @author makejava
 * @since 2020-05-05 20:56:45
 */
public interface CharaterService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Charater queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Charater> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param charater 实例对象
     * @return 实例对象
     */
    Charater insert(Charater charater);

    /**
     * 修改数据
     *
     * @param charater 实例对象
     * @return 实例对象
     */
    Charater update(Charater charater);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Charater> getAll();
}