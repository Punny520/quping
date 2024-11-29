package com.quping.service.Impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.quping.common.Result;
import com.quping.service.FileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MinioFileServiceImpl implements FileService {
    @Value("${minio.bucket}")
    private String bucketName;

    private final MinioClient minioClient;

    @Autowired
    MinioFileServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    @SneakyThrows
    public Result upload(MultipartFile file){
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(getFileName(file))
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        return Result.ok();
    }
    private String getFileName(MultipartFile file){
        String oldName = file.getOriginalFilename();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String randomStr = RandomUtil.randomString(6);
        return timestamp + '-' + randomStr + oldName.substring(oldName.lastIndexOf("."));
    }
}
