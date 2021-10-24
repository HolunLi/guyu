package com.holun.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.holun.oss.service.OssService;
import com.holun.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileToAliyunOss(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        OSS ossClient = null;
        InputStream inputStream = null;
        try {
            //创建OSSClient对象
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取上传文件的输入流
            inputStream = file.getInputStream();
            //获取UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //获取上传文件的名字，再加上UUID，以防止上传的文件同名（如果文件同名，会导致后面上传的覆盖前面上传的）
            String fileName = uuid + file.getOriginalFilename();
            //将上传的文件按日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;

            //上传文件到oss
            ossClient.putObject(bucketName, fileName, inputStream);
            //https://edu-2021-10-01.oss-cn-shanghai.aliyuncs.com/loginBackground.png
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url; //返会图片上传到oss上的地址
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null)
                //关闭OSSClient
                ossClient.shutdown();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
