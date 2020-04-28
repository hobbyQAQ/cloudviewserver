package com.example.cloudviewserver.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoUtil {
    //http://localhost:8089/img/static/1/IMG_20180215_193102.jpg
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static Date Path2Date(String url,Integer uid) throws ParseException {
        String match = url.replace("/static/"+uid+"/IMG_","").replace(".jpg","");
        Date date = simpleDateFormat.parse(match);
        return date;
    }
    //D:\cloudviewserver\target\classes\static\1\IMG_20180215_191434.jpg
    public static String getPath(String absolutePath){
        return absolutePath.replace("D:\\cloudviewserver\\target\\classes","").replace("\\","/");
    }

    public static String Path2Name(String path,Integer uid){
        return path.replace("/static/"+uid+"/","");
    }


    public static boolean isMatchedFilename(String fileName){
            return fileName.contains("IMG");
    }

}
