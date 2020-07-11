package com.leyou.upload.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author liang
 * @create 2020/4/28 17:39
 */
@Service
public class UploadService {
//    创建一个数组用来放置上传图片类型的contentType
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg","application/x-jpg");
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);
    public String upload(MultipartFile file) {
//        校验文件大小，在spring配置文件中已经配置好最大文件大小
//        获取原始文件名
        String originalFilename = file.getOriginalFilename();

//        校验文件的后缀是否为图片（contentType）
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)){
            LOGGER.info("文件类型不合法:"+ originalFilename);
            return null;
        }
//        校验文件内容是否合格
        //获取图片文件的字节流，如果为null，文件不合法
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage==null){
                LOGGER.info("文件内容不合法："+originalFilename);
                return null;
            }
            file.transferTo(new File("B:\\javawork\\leyou\\images\\"+originalFilename));

//            System.out.println(originalFilename);
            return "http://image.leyou.com/"+originalFilename;
        } catch (IOException e) {
            LOGGER.info("服务器内部错误:"+originalFilename);
            e.printStackTrace();
        }

//        返回一个回显图片路径
    return null;
    }
}
