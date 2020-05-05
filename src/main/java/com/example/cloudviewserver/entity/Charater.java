package com.example.cloudviewserver.entity;

import java.io.Serializable;

/**
 * (Charater)实体类
 *
 * @author makejava
 * @since 2020-05-05 20:56:45
 */
public class Charater implements Serializable {
    private static final long serialVersionUID = -36641760899874491L;
    /**
    * 人物的id
    */
    private Integer id;
    /**
    * 人物的名字
    */
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}