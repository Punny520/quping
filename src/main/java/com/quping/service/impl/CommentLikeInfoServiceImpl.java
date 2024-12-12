package com.quping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quping.entity.CommentLikeInfo;
import com.quping.entity.User;
import com.quping.service.CommentLikeInfoService;
import com.quping.dao.mapper.CommentLikeInfoMapper;
import com.quping.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author punny
* @description 针对表【comment_like_info】的数据库操作Service实现
* @createDate 2024-12-06 17:01:40
*/
@Service
public class CommentLikeInfoServiceImpl extends ServiceImpl<CommentLikeInfoMapper, CommentLikeInfo>
    implements CommentLikeInfoService{
    private CommentLikeInfoMapper commentLikeInfoMapper;
    @Autowired
    CommentLikeInfoServiceImpl(CommentLikeInfoMapper commentLikeInfoMapper) {
        this.commentLikeInfoMapper = commentLikeInfoMapper;
    }
    /**
     * 根据commentId和userId获取用户点赞状态
     * @param commentId
     * @return
     */
    @Override
    public Boolean getStatus(Long commentId) {
        User user = UserHolder.getUserSession();
        if (user == null) {
            return false;
        }
        CommentLikeInfo entity = new CommentLikeInfo();
        entity.setCommentId(commentId);
        entity.setUserId(user.getId());
        CommentLikeInfo likeInfo = commentLikeInfoMapper.selectOne(new QueryWrapper<>(entity));
        if(likeInfo == null) {
            return false;
        }
        return likeInfo.getStatus() == 1 ? Boolean.TRUE : Boolean.FALSE;
    }
}




