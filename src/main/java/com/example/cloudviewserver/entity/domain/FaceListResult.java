package com.example.cloudviewserver.entity.domain;

import java.util.List;

public class FaceListResult {


    /**
     * error_code : 0
     * error_msg : SUCCESS
     * log_id : 7500110165759
     * timestamp : 1590724896
     * cached : 0
     * result : {"face_list":[{"face_token":"79546d104923a1c892ee26f565673797","ctime":"2020-05-29 09:43:20"},{"face_token":"1b3fa27dbe8edab71f8c9e2ff4da021a","ctime":"2020-05-29 09:45:46"},{"face_token":"db6ab182e9627793232bd2f8ab075d1b","ctime":"2020-05-29 09:45:47"}]}
     */

    private int error_code;
    private String error_msg;
    private long log_id;
    private int timestamp;
    private int cached;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getCached() {
        return cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<FaceListBean> face_list;

        public List<FaceListBean> getFace_list() {
            return face_list;
        }

        public void setFace_list(List<FaceListBean> face_list) {
            this.face_list = face_list;
        }

        public static class FaceListBean {
            /**
             * face_token : 79546d104923a1c892ee26f565673797
             * ctime : 2020-05-29 09:43:20
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
}
