package com.example.cloudviewserver.entity;

import java.io.Serializable;

/**
 * (Facemapper)实体类
 *
 * @author makejava
 * @since 2020-05-05 20:56:46
 */
public class Facemapper implements Serializable {
    private static final long serialVersionUID = -30743048866986314L;
    /**
    * 人物的id
    */
    private Integer cid;
    /**
    * 脸的id
    */
    private String faceToken;

    public Facemapper(Integer cid, String faceToken) {
        this.cid = cid;
        this.faceToken = faceToken;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

}