package com.example.cloudviewserver.entity.domain;

import java.util.List;

public class MatchResult {
    /**
     * 人脸匹配的结果
     * score : 44.3
     * face_list : [{"face_token":"fid1"},{"face_token":"fid2"}]
     */

    private double score;
    private List<FaceListBean> face_list;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<FaceListBean> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceListBean> face_list) {
        this.face_list = face_list;
    }

    public static class FaceListBean {
        /**
         * face_token : fid1
         */

        private String face_token;

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }
    }
}
