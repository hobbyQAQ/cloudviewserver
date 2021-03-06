package com.example.cloudviewserver.service.impl;

import com.example.cloudviewserver.dao.PhotomapperDao;
import com.example.cloudviewserver.entity.Photo;
import com.example.cloudviewserver.dao.PhotoDao;
import com.example.cloudviewserver.entity.Photomapper;
import com.example.cloudviewserver.service.PhotoService;
import com.example.cloudviewserver.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Photo)表服务实现类
 *
 * @author makejava
 * @since 2020-04-20 13:51:20
 */
@Service("photoService")
public class PhotoServiceImpl implements PhotoService {
    @Resource
    private PhotoDao photoDao;

    @Resource
    private PhotomapperDao photomapperDao;

    @Override
    public Result getPhotoByCid(int cid, int uid) {
        return Result.success(photoDao.getPhotoByCid(cid,uid));
    }

    /**
     * 获取Type0的照片
     * @return
     */
    @Override
    public List<Photo> getPhotoByType0() {
        return photoDao.getPhotoByType0();
    }

    @Override
    public List<Photo> getPhotoByType2() {
        return photoDao.getPhotoByType2();
    }

    @Override
    public List<Photo> getPhotoByType3() {
        return photoDao.getPhotoByType3();
    }

    /**
     * 将pid的照片添加到收藏
     *
     * @param pid
     * @param isLove
     * @return
     */
    @Override
    public boolean addLove(Integer pid, Integer isLove) {
        if (photoDao.addLove(pid,isLove) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Photo> getLoves(Integer uid) {
        return photoDao.getLoves(uid);
    }


    /**
     * 模糊查询
     * @param keyword
     * @param uid
     * @return
     */
    @Override
    public List<Photo> searchByKeyword(String keyword, Integer uid) {
        return photoDao.searchByKeyword(keyword,uid);
    }

    @Override
    public void insertPhotomapper(Photomapper photomapper) {
        this.photomapperDao.insert(photomapper);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Photo queryById(Integer id) {
        return this.photoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Photo> queryAllByLimit(int offset, int limit) {
        return this.photoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param photo 实例对象
     * @return 实例对象
     */
    @Override
    public Photo insert(Photo photo) {
        this.photoDao.insert(photo);
        return photo;
    }

    /**
     * 修改数据
     *
     * @param photo 实例对象
     * @return 实例对象
     */
    @Override
    public Photo update(Photo photo) {
        this.photoDao.update(photo);
        return this.queryById(photo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.photoDao.deleteById(id) > 0;
    }

    /**
     * 根据用户 的id获取该用户的所有照片
     * @param uid
     * @return
     */
    @Override
    public Result<List<Photo>> getPhotoByUid(Integer uid) {
        List<Photo> photos = photoDao.queryByUid(uid);
        return Result.success(photos);
    }
}