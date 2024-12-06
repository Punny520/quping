package com.quping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quping.common.Result;
import com.quping.dao.mapper.CommentLikeInfoMapper;
import com.quping.dao.mapper.CommentMapper;
import com.quping.dao.mapper.UserMapper;
import com.quping.dto.CommentDTO;
import com.quping.entity.Comment;
import com.quping.entity.CommentLikeInfo;
import com.quping.entity.User;
import com.quping.service.CommentLikeInfoService;
import com.quping.service.CommentService;
import com.quping.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentLikeInfoMapper commentLikeInfoMapper;
    private final CommentLikeInfoService commentLikeInfoService;
    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper,
                              CommentLikeInfoMapper commentLikeInfoMapper,
                              CommentLikeInfoService commentLikeInfoService) {
        this.commentMapper = commentMapper;
        this.commentLikeInfoMapper = commentLikeInfoMapper;
        this.commentLikeInfoService = commentLikeInfoService;
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
            commentDTO.setLiked(commentLikeInfoService.getStatus(commentDTO.getId()));
            return commentDTO;
        }).collect(Collectors.toList());
        return Result.ok(dtoList);
    }

    @Override
    public synchronized Result<String> likeComment(Long commentId) {
        CommentLikeInfo entity = new CommentLikeInfo();
        entity.setCommentId(commentId);
        entity.setUserId(UserHolder.getUserSession().getId());
        CommentLikeInfo likeInfo = commentLikeInfoMapper.selectOne(new QueryWrapper<>(entity));
        Comment comment = commentMapper.selectById(commentId);
        if(comment == null){
            return Result.fail();
        }
        if(likeInfo != null) {
            setLike(likeInfo,comment);
            commentLikeInfoMapper.updateById(likeInfo);
            commentMapper.updateById(comment);
            return Result.ok();
        }
        entity.setStatus(1);
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentLikeInfoMapper.insert(entity);
        commentMapper.updateById(comment);
        return Result.ok();
    }
    private void setLike(CommentLikeInfo likeInfo,Comment comment) {
        if(likeInfo.getStatus() == null) {
            likeInfo.setStatus(0);
        }else {
            //点赞或者取消点赞
            if(likeInfo.getStatus() == 0) {
                likeInfo.setStatus(1);
                comment.setLikeCount(comment.getLikeCount() + 1);
            }else{
                likeInfo.setStatus(0);
                comment.setLikeCount(comment.getLikeCount() - 1);
            }
        }
    }
}
