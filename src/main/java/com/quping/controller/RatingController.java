package com.quping.controller;

import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 插入评分
     * @param ratingDTO
     * @return
     */
    @PostMapping
    public Result addRating(@RequestBody RatingDTO ratingDTO){
        //TODO 参数验证
        return ratingService.insert(ratingDTO);
    }

    /**
     * 根据id获取评分
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getRating(@PathVariable int id){
        return ratingService.getById(id);
    }
}
