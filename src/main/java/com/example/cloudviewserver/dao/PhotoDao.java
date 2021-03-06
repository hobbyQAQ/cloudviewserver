package com.example.cloudviewserver.dao;

import com.example.cloudviewserver.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Photo)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-20 13:51:20
 */
@Mapper
public interface PhotoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Photo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Photo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param photo 实例对象
     * @return 对象列表
     */
    List<Photo> queryAll(Photo photo);

    /**
     * 新增数据
     *
     * @param photo 实例对象
     * @return 影响行数
     */
    int insert(Photo photo);

    /**
     * 修改数据
     *
     * @param photo 实例对象
     * @return 影响行数
     */
    int update(Photo photo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 连接user、photomapper、photo三张表查询
     * @param uid
     * @return
     */
    List<Photo> queryByUid(Integer uid);

    /**
     *  获得所有相片
     */
    List<Photo> selectAll();

    List<Photo> getPhotoByCid(@Param("cid") int cid,@Param("uid") int uid);

    List<Photo> getPhotoByType0();

    List<Photo> getPhotoByType2();

    List<Photo> getPhotoByType3();

    List<Photo> searchByKeyword(@Param("keyword") String keyword, @Param("uid") Integer uid);

    int addLove(@Param("pid")Integer pid,@Param("isLove") Integer isLove);

    List<Photo> getLoves(@Param("uid")Integer uid);
}