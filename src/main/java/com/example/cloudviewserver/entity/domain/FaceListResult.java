package com.example.cloudviewserver.entity.domain;

import java.util.List;

public class FaceListResult {

    /**
     *  记录从人脸库中获取的人脸的结果
     */
    private List<FaceListBean> face_list;

    public List<FaceListBean> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceListBean> face_list) {
        this.face_list = face_list;
    }

    public static class FaceListBean {
        /**
         * face_token : fid1
         * ctime : 2018-01-01 00:00:00
         */

        private String face_token;
        private String ctime;

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }
    }
}
