package com.example.cloudviewserver.service.impl;

import com.example.cloudviewserver.entity.Facemapper;
import com.example.cloudviewserver.dao.FacemapperDao;
import com.example.cloudviewserver.service.FacemapperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Facemapper)表服务实现类
 *
 * @author makejava
 * @since 2020-05-05 20:56:46
 */
@Service("facemapperService")
public class FacemapperServiceImpl implements FacemapperService {
    @Resource
    private FacemapperDao facemapperDao;

    /**
     * 通过ID查询单条数据
     *

     * @return 实例对象
     */
    @Override
    public Facemapper queryByFaceToken(String token) {
        return this.facemapperDao.queryByFaceToken(token);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Facemapper> queryAllByLimit(int offset, int limit) {
        return this.facemapperDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param facemapper 实例对象
     * @return 实例对象
     */
    @Override
    public Facemapper insert(Facemapper facemapper) {
        this.facemapperDao.insert(facemapper);
        return facemapper;
    }


    @Override
    public Facemapper update(Facemapper facemapper) {
        return null;
    }

    @Override
    public int deleteByFaceToken(String token) {
        return facemapperDao.deleteByFaceToken(token);
    }
}