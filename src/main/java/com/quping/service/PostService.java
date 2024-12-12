package com.quping.service;

import com.quping.common.Result;
import com.quping.dto.PostDTO;
import com.quping.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Canway
* @description 针对表【post】的数据库操作Service
* @createDate 2024-12-12 17:34:09
*/
public interface PostService extends IService<Post> {

    Result<?> create(PostDTO postDTO);
}
