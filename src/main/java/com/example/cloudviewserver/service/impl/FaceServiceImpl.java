package com.example.cloudviewserver.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.cloudviewserver.entity.Face;
import com.example.cloudviewserver.dao.FaceDao;
import com.example.cloudviewserver.entity.domain.DetectResult;
import com.example.cloudviewserver.entity.domain.FaceListResult;
import com.example.cloudviewserver.entity.domain.FaceMatch;
import com.example.cloudviewserver.entity.domain.MatchResult;
import com.example.cloudviewserver.service.FaceService;
import com.example.cloudviewserver.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Face)表服务实现类
 *
 * @author makejava
 * @since 2020-05-03 18:48:54
 */
@Service("faceService")
public class FaceServiceImpl implements FaceService {
    @Resource
    private FaceDao faceDao;
    private DetectResult detectResult;
    private FaceListResult faceListResult;

    /**
     * 通过ID查询单条数据
     *
     * @param faceToken 主键
     * @return 实例对象
     */
    @Override
    public Result queryById(String faceToken) {
        Face face = this.faceDao.queryById(faceToken);
        return Result.success(face);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Face> queryAllByLimit(int offset, int limit) {
        return this.faceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param face 实例对象
     * @return 实例对象
     */
    @Override
    public Face insert(Face face) {
        this.faceDao.insert(face);
        return face;
    }

    /**
     * 修改数据
     *
     * @param face 实例对象
     * @return 实例对象
     */
    @Override
    public Result update(Face face) {
        int update = this.faceDao.update(face);
        if (update>0) {
            return Result.success("更新成功");
        }
        return Result.success("更新失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param faceToken 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String faceToken) {
        return this.faceDao.deleteById(faceToken) > 0;
    }

    @Override
    public MatchResult matchFace(String faceToken, String face_token) {
        FaceMatch pic1 = new FaceMatch();
        pic1.setImage(faceToken);
        pic1.setImage_type("TOKEN");
        FaceMatch pic2 = new FaceMatch();
        pic2.setImage(face_token);
        pic2.setImage_type("TOKEN");
        String param1 =  GsonUtils.toJson(pic1);
        String param2 =  GsonUtils.toJson(pic2);
        String param = "[" + param1 + "," + param2 + "]";
        MatchResult matchResult = null;
        try {
            String result = HttpUtil.post(Constants.MATCH_URL, Constants.ACCESS_TOKEN, "application/json", param);
            matchResult = GsonUtils.fromJson(result,MatchResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchResult;
    }

    @Override
    public FaceListResult getFaceListFromBaidu() {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/face/getlist";
        FaceListResult faceListResult = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", "wuyubin");
            map.put("group_id", "1");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Constants.ACCESS_TOKEN;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            faceListResult = (FaceListResult) JSONUtils.parse(result);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faceListResult;
    }

    @Override
    public String faceUpdate2Baidu(String filePath, Integer uid, Integer cid) {
        return null;
    }

    @Override
    public String faceAdd2Baidu(String filePath , Integer uid, Integer cid) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        String image = Base64ImageUtil.GetImageStrFromPath(filePath);
        Map<String, Object> map = new HashMap<>();
        map.put("image", image);
        map.put("group_id", uid);
        map.put("user_id", cid);
        map.put("max_face_num", 10);
        map.put("liveness_control", "NONE");
        map.put("image_type", "BASE64");
        map.put("quality_control", "LOW");
        String param = GsonUtils.toJson(map);
        // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
        String accessToken = Constants.ACCESS_TOKEN;
        String result = null;
        try {
            result = HttpUtil.post(url, accessToken, "application/json", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return null;
    }

    @Override
    public DetectResult faceDetect(String filePath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        DetectResult detectResult = null;
        try {
            String image = Base64ImageUtil.GetImageStrFromPath(filePath);
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
            map.put("image_type", "BASE64");
            map.put("max_face_num", 8);
            map.put("face_type", "LIVE");
            String param = GsonUtils.toJson(map);
            String accessToken = Constants.ACCESS_TOKEN;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            detectResult = GsonUtils.fromJson(result,DetectResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return detectResult;
    }


}