package com.example.cloudviewserver.entity;

import java.io.Serializable;

/**
 * (Face)实体类
 *
 * @author makejava
 * @since 2020-05-03 18:48:54
 */
public class Face implements Serializable {
    private static final long serialVersionUID = -97957068123273559L;
    //2020/5/5 uid可以通过连接查询

    //2020/5/5 加了一个旋转角度
    /**
    * 人脸唯一标识
    */
    private String faceToken;
    /**
    * 人脸区域离左边界的距离
    */
    private Double left;
    /**
    * 	人脸区域离上边界的距离
    */
    private Double top;
    /**
    * 人脸区域的宽度
    */
    private Double width;
    /**
    * 人脸区域的高度
    */
    private Double height;
    /**
    * 人脸对应在那张相片中
    */
    private Integer pid;

    /**
    * 人脸框相对于竖直方向的旋转角度
    */
    private Integer rotation;

    /**
     * 记录剪切过的人脸图片的所在位置
     */
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }

}