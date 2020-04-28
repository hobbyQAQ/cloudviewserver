# Springboot框架写的云相册app的后台

## 一、云影API文档

#### baseUrl:

http://192.168.0.2（目前没有服务器本地测试用的）

#### 获取所有照片

方法：get

接口：/photo/get

参数：uid（用户id）

返回

```json
{
    "success": true,
    "msg": "OK",
    "data": [
        {
            "id": 1,
            "date": "2020-04-20T08:30:48.000+0000",
            "location": "南平",
            "path": "/static/1/IMG_20180215_193102.jpg"
        },
        {
            "id": 2,
            "date": "2020-04-15T08:54:34.000+0000",
            "location": "上海",
            "path": "/static/1/IMG_20180215_191537.jpg"
        },
        {
            "id": 3,
            "date": "2020-04-09T13:16:03.000+0000",
            "location": "南昌",
            "path": "/static/1/IMG_20180215_193102.jpg"
        },
        {
            "id": 4,
            "date": "2019-03-03T04:07:44.000+0000",
            "location": null,
            "path": "/static/1/IMG_20190303_120744.jpg"
        },
        {
            "id": 5,
            "date": "2019-03-16T06:47:46.000+0000",
            "location": null,
            "path": "/static/1/IMG_20190316_144746.jpg"
        },
        {
            "id": 6,
            "date": "2019-03-18T07:48:26.000+0000",
            "location": null,
            "path": "/static/1/IMG_20190318_154826.jpg"
        },
        {
            "id": 7,
            "date": "2019-03-22T00:54:48.000+0000",
            "location": null,
            "path": "/static/1/IMG_20190322_085448.jpg"
        },
        {
            "id": 8,
            "date": "2019-03-22T01:47:11.000+0000",
            "location": null,
            "path": "/static/1/IMG_20190322_094711.jpg"
        },
        {
            "id": 9,
            "date": "2019-04-15T11:11:10.000+0000",
            "location": null,
            "path": "/static/1/IMG_20190415_191110.jpg"
        },
        {
            "id": 10,
            "date": "2019-04-23T12:20:27.000+0000",
            "location": null,
            "path": "/static/1/IMG_20190423_202027.jpg"
        }
    ]
}
```



#### 单文件上传

- 方法：post
- 接口：/photo/upload
- 参数：file （文件必须是图片类型）uid（用户id）
- 返回

```
{
    "success": true,
    "msg": "上传文件成功,
    "data": null
}
```



#### 多文件上传

- 方法：post

- 接口：/photo/uploads

- 参数：files（文件可以为多个，但不能超过100MB）

  ​			uid（用户id）

- 返回

```
{
    "success": true,
    "msg": "上传文件成功,
    "data": null
}
```



#### 单文件下载

- 方法：get

- 接口：/photo/download

- 参数：files（文件可以为多个，但不能超过100MB）

  ​			uid（用户id）

- 返回

```
{
    "success": true,
    "msg": "文件下载成功,
    "data": null
}
```

