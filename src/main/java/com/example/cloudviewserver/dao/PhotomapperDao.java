package com.example.cloudviewserver.dao;

import com.example.cloudviewserver.entity.Photomapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Photomapper)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-20 13:51:12
 */
@Mapper
public interface PhotomapperDao {

    void insert( Photomapper photomapper);
}