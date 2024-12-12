package com.quping.controller;

import com.quping.common.Result;
import com.quping.dto.PostDTO;
import com.quping.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody PostDTO postDTO) {
        return postService.create(postDTO);
    }
}
