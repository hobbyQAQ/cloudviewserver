package com.example.cloudviewserver.service;

import com.example.cloudviewserver.entity.Facemapper;
import java.util.List;

/**
 * (Facemapper)表服务接口
 *
 * @author makejava
 * @since 2020-05-05 20:56:46
 */
public interface FacemapperService {

    /**
     *
     * @param
     * @return 实例对象
     */
    Facemapper queryByFaceToken(String faceToken);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Facemapper> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param facemapper 实例对象
     * @return 实例对象
     */
    Facemapper insert(Facemapper facemapper);

    /**
     * 修改数据
     *
     * @param facemapper 实例对象
     * @return 实例对象
     */
    Facemapper update(Facemapper facemapper);

    /**
     * 通过主键删除数据
     *
     * @param
     * @return 是否成功
     */
    int deleteByFaceToken(String token);

}