package com.example.cloudviewserver.service.impl;

import com.example.cloudviewserver.entity.Charater;
import com.example.cloudviewserver.dao.CharaterDao;
import com.example.cloudviewserver.service.CharaterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Charater)表服务实现类
 *
 * @author makejava
 * @since 2020-05-05 20:56:45
 */
@Service("charaterService")
public class CharaterServiceImpl implements CharaterService {
    @Resource
    private CharaterDao charaterDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Charater queryById(Integer id) {
        return this.charaterDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Charater> queryAllByLimit(int offset, int limit) {
        return this.charaterDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param charater 实例对象
     * @return 实例对象
     */
    @Override
    public Charater insert(Charater charater) {
        this.charaterDao.insert(charater);
        return charater;
    }

    /**
     * 修改数据
     *
     * @param charater 实例对象
     * @return 实例对象
     */
    @Override
    public Charater update(Charater charater) {
        this.charaterDao.update(charater);
        return this.queryById(charater.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.charaterDao.deleteById(id) > 0;
    }
}