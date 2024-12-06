package com.quping.service;

import com.quping.entity.CommentLikeInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Canway
* @description 针对表【comment_like_info】的数据库操作Service
* @createDate 2024-12-06 17:01:40
*/
public interface CommentLikeInfoService extends IService<CommentLikeInfo> {

    Boolean getStatus(Long commentId);
}
