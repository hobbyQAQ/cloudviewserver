package com.example.cloudviewserver.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Photo)实体类
 *
 * @author makejava
 * @since 2020-04-20 13:51:20
 */
public class Photo implements Serializable {
    private static final long serialVersionUID = -41789137409142961L;
    /**
    * 相片唯一标识id，由时间戳构成
    */
    private Integer id;
    /**
    * 相片何时拍的
    */
    private Date date;
    /**
    * 相片在哪儿拍的
    */
    private String location;
    /**
    * 相片的存放位置
    */
    private String path;

    /**
     * 照片类型 0 风景 1 人物 2 动物 3 证件
     */
    private Integer type;

    private Integer love;

    public Integer getLove() {
        return love;
    }

    public void setLove(Integer love) {
        this.love = love;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}