package com.quping.controller;

import com.quping.common.PageInfo;
import com.quping.common.PageResult;
import com.quping.common.Result;
import com.quping.dto.CommentDTO;
import com.quping.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论相关Controller
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    /**
     * 用户发表评论
     * @param commentDTO
     * @return
     */
    @PostMapping("/post")
    public Result<String> postComment(@RequestBody CommentDTO commentDTO) {
        return commentService.post(commentDTO);
    }

    /**
     * 列出当前评分下的所有评论
     * @param ratingId
     * @return
     */
    @GetMapping("/list/{ratingId}")
    public Result<List<CommentDTO>> listComment(@PathVariable Long ratingId) {
        return commentService.listByRatingId(ratingId);
    }

    /**
     * 点赞评论
     * @param commentId
     * @return
     */
    @GetMapping("/like/{commentId}")
    public Result<String> likeComment(@PathVariable Long commentId) {
        return commentService.likeComment(commentId);
    }

    /**
     * 评分下评论分页加载
     * @param ratingId
     * @return
     */
    @GetMapping("/list/page/{ratingId}")
    public Result<PageResult<CommentDTO>> listByPage(@RequestBody PageInfo pageInfo,
                                                     @PathVariable Long ratingId) {
        return commentService.listPage(pageInfo,ratingId);
    }

}
