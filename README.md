# Springboot框架写的云相册app的后台

## 一、云影API文档

#### baseUrl:

http://192.168.0.2（目前没有服务器本地测试用的）

#### 登录

方法：get

接口：/user/login

参数：account

​			password

返回 

```
{
    "success": true,
    "msg": "OK",
    "data": {
        "id": 1,
        "account": "942449150",
        "password": "123456",
        "nickname": "头铁的小伙子",
        "coverPath": "cover/1/pic_5.jpeg"
    }
}
```





#### 上传头像

方法：post

接口：/user/upload

参数：uid（用户id）

​			file 头像文件

返回 

```
{
    "success": true,
    "msg": "OK",
    "data": {
        "id": 1,
        "account": "942449150",
        "password": "123456",
        "nickname": "头铁的小伙子",
        "coverPath": "cover/1/pic_5.jpeg"
    }
}
```





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



#### 获取所有人脸图片

- 方法：get

- 接口：                 

- 参数：uid（用户id）

- 返回

```
{
    "success": true,                      
    "msg": "OK",
    "data": [
        {
            "faceToken": "076d0f505c694f3b09a5a66021a97ca9",
            "left": 1571.0,
            "top": 2012.0,
            "width": 100.0,
            "height": 100.0,
            "pid": 7,
            "rotation": -88,
            "path": "/face/1/076d0f505c694f3b09a5a66021a97ca9.jpg"
        },
        {
            "faceToken": "4562220837188aa5d1fa3d270bb70fcf",
            "left": 930.0,
            "top": 1941.0,
            "width": 97.0,
            "height": 94.0,
            "pid": 12,
            "rotation": -110,
            "path": "/face/1/4562220837188aa5d1fa3d270bb70fcf.jpg"
        },
        {
            "faceToken": "5c734e21f81795cb3061bd4c7722d691",
            "left": 196.0,
            "top": 175.0,
            "width": 66.0,
            "height": 61.0,
            "pid": 11,
            "rotation": -4,
            "path": "/face/1/5c734e21f81795cb3061bd4c7722d691.jpg"
        },
        {
            "faceToken": "845a72cea366eaf1be04ec12d3f9715e",
            "left": 1737.0,
            "top": 1225.0,
            "width": 102.0,
            "height": 104.0,
            "pid": 13,
            "rotation": -8,
            "path": "/face/1/845a72cea366eaf1be04ec12d3f9715e.jpg"
        },
        {
            "faceToken": "9c021c9db96386974de011f6598aae7d",
            "left": 70.0,
            "top": 2168.0,
            "width": 51.0,
            "height": 45.0,
            "pid": 3,
            "rotation": 5,
            "path": "/face/1/9c021c9db96386974de011f6598aae7d.jpg"
        },
        {
            "faceToken": "aa403dd946a340f7755dca0d9542a04d",
            "left": 1497.0,
            "top": 2321.0,
            "width": 172.0,
            "height": 163.0,
            "pid": 9,
            "rotation": -122,
            "path": "/face/1/aa403dd946a340f7755dca0d9542a04d.jpg"
        },
        {
            "faceToken": "bb7f2d923c1ab0e2879fc2d32c0ed41d",
            "left": 1430.0,
            "top": 1211.0,
            "width": 96.0,
            "height": 110.0,
            "pid": 13,
            "rotation": 0,
            "path": "/face/1/bb7f2d923c1ab0e2879fc2d32c0ed41d.jpg"
        },
        {
            "faceToken": "c1e12a6d7f8c399a9a33a7a594b419c1",
            "left": 1831.0,
            "top": 1869.0,
            "width": 465.0,
            "height": 451.0,
            "pid": 1,
            "rotation": 0,
            "path": "/face/1/c1e12a6d7f8c399a9a33a7a594b419c1.jpg"
        },
        {
            "faceToken": "f278f3402a3aa9e916083d0775c0f696",
            "left": 1507.0,
            "top": 2151.0,
            "width": 493.0,
            "height": 486.0,
            "pid": 2,
            "rotation": -6,
            "path": "/face/1/f278f3402a3aa9e916083d0775c0f696.jpg"
        }
    ]
}
```



#### 获取人物的所有照片

- 方法：get

- 接口：/photo/getbyface

- 参数：faceToken

- 返回

```
{
    "success": true,
    "msg": "获取成功",
    "data": null
}
```



#### 删除照片

- 方法：get

- 接口：/photo/delete

- 参数：uid

  ​			pid

- 返回

```
{
    "success": true,
    "msg": "删除成功",
    "data": null
}
```



#### 修改人物的命名

- 方法：get

- 接口：/face/update/name

- 参数：faceToken

  ​			faceName

- 返回

```
{
    "success": true,
    "msg": "修改成功",
    "data": null
}
```





#### 根据人脸获得相应的照片

- 方法：get

- 接口：/photo/get/by/face

- 参数：cid

- 返回

```
{
    "success": true,
    "msg": "OK",
    "data": [
        {
            "id": 1,
            "date": "2018-02-15T11:31:02.000+0000",
            "location": "福建南平",
            "path": "/static/1/IMG_20180215_193102.jpg",
            "type": 1
        },
        {
            "id": 2,
            "date": "2018-02-15T11:15:37.000+0000",
            "location": "上海",
            "path": "/static/1/IMG_20180215_191537.jpg",
            "type": 1
        }
    ]
}
```



#### 根据人物搜索照片

- 方法：get

- 接口：/photo/search/by/face

- 参数：cid

- 返回

```
{
    "success": true,
    "msg": "OK",
    "data": [
        {
            "id": 1,
            "date": "2018-02-15T11:31:02.000+0000",
            "location": "福建南平",
            "path": "/static/1/IMG_20180215_193102.jpg",
            "type": 1
        },
        {
            "id": 2,
            "date": "2018-02-15T11:15:37.000+0000",
            "location": "上海",
            "path": "/static/1/IMG_20180215_191537.jpg",
            "type": 1
        }
    ]
}
```





### 二、核心功能的实现

##### 1.如何实现上传下载是的进度条

#####  2.图片裁剪框架

```
Thumbnails
```



