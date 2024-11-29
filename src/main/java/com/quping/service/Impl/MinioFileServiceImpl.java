package com.quping.service.Impl;

import com.quping.common.Result;
import com.quping.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(file.getName())
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        return Result.ok();
    }
}
