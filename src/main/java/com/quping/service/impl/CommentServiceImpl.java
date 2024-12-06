package com.quping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quping.common.Result;
import com.quping.dao.mapper.CommentMapper;
import com.quping.dto.CommentDTO;
import com.quping.entity.Comment;
import com.quping.entity.User;
import com.quping.service.CommentService;
import com.quping.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
    @Override
    public Result<String> post(CommentDTO commentDTO) {
        User user = UserHolder.getUserSession();
        commentDTO.setUserId(user.getId());
        commentDTO.setLikeCount(0);
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentDTO,comment);
        int res = commentMapper.insert(comment);
        return res == 0 ? Result.failWithMsg("失败") : Result.ok();
    }

    @Override
    public Result<List<CommentDTO>> listByRatingId(Long ratingId) {
        List<Comment> list = commentMapper.selectList(new QueryWrapper<Comment>()
                .eq("rating_id", ratingId));
        List<CommentDTO> dtoList = list.stream().map(e -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtil.copyProperties(e, commentDTO);
            return commentDTO;
        }).collect(Collectors.toList());
        return Result.ok(dtoList);
    }
}
