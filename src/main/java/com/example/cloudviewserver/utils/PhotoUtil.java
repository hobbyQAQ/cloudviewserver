package com.example.cloudviewserver.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoUtil {
    //http://localhost:8089//upload/photo/3/IMG_20191001_095558.jpg
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    // photo/1/IMG_20190322_094711.jpg
    public static Date Path2Date(String url, Integer uid) throws ParseException {
        String match = url.replace("photo/" + uid + "/IMG_", "").replace(".jpg", "");
        Date date = simpleDateFormat.parse(match);
        return date;
    }

    //
    public static String getPath(String absolutePath) {
        return absolutePath.replace("\\", "/").replace("D:/yunyin/upload/", "");
    }

    public static String getFacePath(String absolutePath) {
        return absolutePath.replace("\\", "/").replace("D:/yunyin/upload/", "");
    }

    public static String Path2Name(String path, Integer uid) {
        return path.replace("photo/" + uid + "/", "");
    }

    /**
     * 是否是匹配的文件格式
     *
     * @param fileName
     * @return
     */
    public static boolean isMatchedFilename(String fileName) {
        return fileName.contains("IMG");
    }

    /**
     * 图像裁剪
     *
     * @param src    图片
     * @param x      起始x
     * @param y      起始y
     * @param width
     * @param height
     * @return 剪裁过后的BufferedImage
     */
    public static BufferedImage img_tailor(BufferedImage src, int x, int y, int width, int height) {
        BufferedImage cutedImage = null;
        CropImageFilter cropFilter = new CropImageFilter(x, y, width, height);
        Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(src.getSource(), cropFilter));
        cutedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = cutedImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return cutedImage;
    }

    public static BufferedImage img_rotation(BufferedImage src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2 = res.createGraphics();
        // transform
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        g2.drawImage(src, null, null);
        return res;

    }

    public static Rectangle CalcRotatedSize(Rectangle src, double angel) {
        // if angel is greater than 90 degree, we need to do some conversion
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        len_dalta_width = len_dalta_width > 0 ? len_dalta_width : -len_dalta_width;

        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        len_dalta_height = len_dalta_height > 0 ? len_dalta_height : -len_dalta_height;

        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        des_width = des_width > 0 ? des_width : -des_width;
        des_height = des_height > 0 ? des_height : -des_height;
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }


    /**
     * 读取图片
     */
    public static BufferedImage file2img(String imgpath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imgpath));
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 保存图片,extent为格式，"jpg"、"png"等
     */
    public static void img2file(BufferedImage img, String extent, String newfile) {
        try {
            ImageIO.write(img, extent, new File(newfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
