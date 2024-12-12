package com.quping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quping.common.Result;
import com.quping.dto.PostDTO;
import com.quping.entity.Post;
import com.quping.entity.User;
import com.quping.service.PostService;
import com.quping.dao.mapper.PostMapper;
import com.quping.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Canway
* @description 针对表【post】的数据库操作Service实现
* @createDate 2024-12-12 17:34:09
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{
    private PostMapper postMapper;
    @Autowired
    public void setPostMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }
    @Override
    public Result<?> create(PostDTO postDTO) {
        User user = UserHolder.getUserSession();
        if(user == null){
            return Result.failWithMsg("登录后重试");
        }
        Post post = new Post();
        BeanUtil.copyProperties(postDTO, post);
        post.setCreateBy(user.getId());
        post.setLikeCount(0);
        int res = postMapper.insert(post);
        return res == 0 ? Result.fail() : Result.ok();
    }
}




