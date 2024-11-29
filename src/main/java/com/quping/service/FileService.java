package com.quping.service;

import com.quping.common.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Result upload(MultipartFile file);
}
