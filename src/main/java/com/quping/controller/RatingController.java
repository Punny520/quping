package com.quping.controller;

import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 评分修改Controller
 * @author: Punny
 * @date: 2024/9/10 21:25
 */
@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @PostMapping
    public Result addRating(@RequestBody RatingDTO ratingDTO){
        //TODO 参数验证
        return ratingService.insert(ratingDTO);
    }
}
