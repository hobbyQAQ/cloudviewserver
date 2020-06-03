package com.example.cloudviewserver;

import com.example.cloudviewserver.dao.FaceDao;
import com.example.cloudviewserver.dao.PhotoDao;
import com.example.cloudviewserver.entity.Face;
import com.example.cloudviewserver.entity.Photo;
import com.example.cloudviewserver.entity.domain.DetectResult;
import com.example.cloudviewserver.entity.domain.FaceMatch;
import com.example.cloudviewserver.utils.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.bcel.Const;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class CloudviewserverApplicationTests {

    @Resource
    private PhotoDao photoDao;

    @Resource
    private FaceDao faceDao;

    @Test
    void contextLoads() {

    }

    void carLicense() {
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        try {
            // 本地文件路径
            String filePath = "C:\\Users\\94244\\Desktop\\timg.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Constants.ACCESS_TOKEN;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void cutTest() {
        List<Face> faces = faceDao.queryAll();
        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.get(i);
            Photo photo = photoDao.queryById(face.getPid());
            String path = "D:/yunyin/upload/";
            String imgPath = path + photo.getPath();
            BufferedImage srcBfi = PhotoUtil.file2img(imgPath);

//            Graphics2D g = srcBfi.createGraphics();
//            g.setColor(Color.BLUE);//画笔颜色
//            g.setStroke(new BasicStroke(5.0f));
//            g.drawRect(face.getLeft().intValue(),
//                    face.getTop().intValue(),
//                    face.getWidth().intValue(),
//                    face.getHeight().intValue());


            BufferedImage cutImage = PhotoUtil.img_tailor(srcBfi
                    , face.getLeft().intValue() * 19 / 20
                    , face.getTop().intValue() * 19 / 20
                    , face.getWidth().intValue() * 5 / 2
                    , face.getHeight().intValue() * 5 / 2);
            if (face.getRotation() <= -80) {
//                cutImage = PhotoUtil.img_tailor(srcBfi
//                        ,face.getLeft().intValue()
//                        ,face.getTop().intValue()
//                        ,face.getWidth().intValue() * 5/2
//                        ,face.getHeight().intValue()*5/2);
                //小于-80旋转回来
//                cutImage = PhotoUtil.img_rotation(cutImage, 90);
            }
            String destPath = "D:/yunyin/upload/face/1/" + face.getFaceToken() + ".jpg";
            PhotoUtil.img2file(cutImage, "jpg", destPath);
        }


    }

    @Test
    void getAuthTest() {
        String auth = AuthService.getAuth("KGZIcUtvWHDcBvpHehaxSeYF", "LiGD3Md6IzjPynYwob9IT0Ow8gAl7es7");
        System.out.println("auth_token:" + auth);
    }


    @Test
    public void faceDetectAll() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        List<Photo> photos = photoDao.selectAll();
        try {
            String path = "D:\\yunyin\\upload\\photo\\1";
            for (int i = 0; i < photos.size(); i++) {
                Photo photo = photos.get(i);
                String filePath = path + photos.get(i).getPath();
//                Thumbnails.of(filePath)
//                        .scale(1f)
//                        .outputQuality(0.8f)
//                        .toFile(filePath);
//                System.out.println(photos.get(i).getId() + "===>" + filePath);
                String image = Base64ImageUtil.GetImageStrFromPath(filePath);
                Map<String, Object> map = new HashMap<>();
                map.put("image", image);
                map.put("image_type", "BASE64");
                map.put("max_face_num", 10);
                map.put("face_type", "LIVE");
                String param = GsonUtils.toJson(map);
                // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
                String accessToken = Constants.ACCESS_TOKEN;
                String result = HttpUtil.post(url, accessToken, "application/json", param);

                DetectResult detectResult = GsonUtils.fromJson(result, DetectResult.class);
                if (detectResult.getError_code() == 0) {
                    List<DetectResult.ResultBean.FaceListBean> face_list = detectResult.getResult().getFace_list();
                    System.out.println("pid为" + photo.getId() + "的相片有" + detectResult.getResult().getFace_num() + "张人脸");
                    for (int j = 0; j < face_list.size(); j++) {
                        DetectResult.ResultBean.FaceListBean faceListBean = face_list.get(j);
                        System.out.println("face_token" + j + "===" + faceListBean.getFace_token());
                        Face face = new Face();
                        face.setFaceToken(faceListBean.getFace_token());
                        face.setHeight((double) faceListBean.getLocation().getHeight());
                        face.setWidth((double) faceListBean.getLocation().getWidth());
                        face.setLeft((double) faceListBean.getLocation().getLeft());
                        face.setTop((double) faceListBean.getLocation().getTop());
                        face.setPid(photo.getId());
                        face.setRotation(faceListBean.getLocation().getRotation());
                        int res = faceDao.insert(face);
                    }
                    photo.setType(1);
                } else {
                    System.out.println("pid为" + photo.getId() + "的相片没有人脸");
                    photo.setType(0);
                }
//                photoDao.update(photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//{"error_code":222202,"error_msg":"pic not has face","log_id":8910115756545,"timestamp":1590901723,"cached":0,"result":null}
    @Test
    public void faceAddAll() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
//        List<Photo> photos = photoDao.selectAll();
        try {
            String path = "D:/yunyin/upload/photo/1/IMG_20200512_183141.jpg";
//            for (int i = 0; i < photos.size(); i++) {
//                String filePath = path + photos.get(i).getPath();
//                System.out.println(i + "===>" + filePath);
                String image = Base64ImageUtil.GetImageStrFromPath(path);
                Map<String, Object> map = new HashMap<>();
                map.put("image", image);
                map.put("group_id", "1");
                map.put("user_id", 1);
                map.put("max_face_num", 7);
                map.put("liveness_control", "NONE");
                map.put("image_type", "BASE64");
                map.put("quality_control", "LOW");
                String param = GsonUtils.toJson(map);
                // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
                String accessToken = Constants.ACCESS_TOKEN;
                String result = HttpUtil.post(url, accessToken, "application/json", param);
                System.out.println(result);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void faceMatch() {
        //文件路径
        String filePath = "D:\\photo\\IMG_20180913_205440_HHT.jpg";
        String filePathComp = "D:\\photo\\IMG_20180913_184253.jpg";
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            String image = Base64ImageUtil.GetImageStrFromPath(filePath);
            String imageComp = Base64ImageUtil.GetImageStrFromPath(filePathComp);
            FaceMatch pic = new FaceMatch();
            pic.setImage(imageComp);
            pic.setImage_type("BASE64");
            pic.setFace_type("LIVE");
            pic.setLiveness_control("NONE");
            pic.setQuality_control("LOW");
            String param1 = GsonUtils.toJson(pic);
            FaceMatch pic2 = new FaceMatch();
            pic2.setImage(image);
            pic2.setImage_type("BASE64");
            pic2.setFace_type("LIVE");
            pic2.setLiveness_control("NONE");
            pic2.setQuality_control("LOW");
            String param = GsonUtils.toJson(pic);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Constants.ACCESS_TOKEN;
            String map = "[" + param + "," + param1 + "]";
            String result = HttpUtil.post(url, accessToken, "application/json", map);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void faceGetList() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/face/getlist";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", "1");
            map.put("group_id", "1");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Constants.ACCESS_TOKEN;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
