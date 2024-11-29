package com.quping.service;

import com.quping.common.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传文件返回url
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
