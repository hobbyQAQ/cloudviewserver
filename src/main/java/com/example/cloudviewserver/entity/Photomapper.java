package com.example.cloudviewserver.entity;

import java.io.Serializable;

/**
 * (Photomapper)实体类
 *
 * @author makejava
 * @since 2020-04-20 13:51:05
 */
public class Photomapper implements Serializable {
    private static final long serialVersionUID = -58672396488306035L;
    /**
    * photo_id
    */
    private Integer pid;
    /**
    * user_id
    */
    private Integer uid;

    public Photomapper(Integer pid, Integer uid) {
        this.pid = pid;
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}