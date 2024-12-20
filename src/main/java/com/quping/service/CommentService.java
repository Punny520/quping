package com.quping.service;

import com.quping.common.PageInfo;
import com.quping.common.PageResult;
import com.quping.common.Result;
import com.quping.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    Result<String> post(CommentDTO commentDTO);

    Result<List<CommentDTO>> listByRatingId(Long ratingId);

    Result<String> likeComment(Long commentId);

    Result<PageResult<CommentDTO>> listPage(PageInfo pageInfo, Long ratingId);
}
