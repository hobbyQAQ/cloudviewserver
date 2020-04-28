package com.example.cloudviewserver.schdule;

import com.example.cloudviewserver.dao.PhotoDao;
import com.example.cloudviewserver.entity.Photo;
import com.example.cloudviewserver.utils.PhotoUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class UpdatePhotoTask {

    @Resource
    private PhotoDao photoDao;

    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() {
        // TODO: 2020/4/25
        List<Photo> photos = photoDao.selectAll();
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).getDate() == null) {
                try {
                    Date date = PhotoUtil.Path2Date(photos.get(i).getPath(),1);
                    photos.get(i).setDate(date);
                    photoDao.update(photos.get(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
//        photoService.updatePhotoDateById(int id,Date date)
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
