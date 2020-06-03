package com.example.cloudviewserver.entity.domain;

import com.example.cloudviewserver.entity.Face;

import java.util.List;

public class Face2 {
    Integer cid;
    String name;
    List<Face> facelist;

    public Face2() {
    }

    public Face2(Integer cid, List<Face> facelist) {
        this.cid = cid;
        this.facelist = facelist;
    }

    public Face2(Integer cid, String name, List<Face> facelist) {
        this.cid = cid;
        this.name = name;
        this.facelist = facelist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public List<Face> getFacelist() {
        return facelist;
    }

    public void setFacelist(List<Face> facelist) {
        this.facelist = facelist;
    }

}
