package com.quping.controller;

import cn.hutool.core.bean.BeanUtil;
import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entry.User;
import com.quping.service.RatingService;
import com.quping.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 评分相关Controller
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
    public Result<Void> addRating(@RequestBody RatingDTO ratingDTO){
        //TODO 参数验证
        return ratingService.insert(ratingDTO);
    }

    /**
     * 根据id获取评分
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<RatingDTO> getRating(@PathVariable int id){
        RatingDTO ratingDTO = new RatingDTO();
        BeanUtil.copyProperties(ratingService.getById(id),ratingDTO);
        User user = UserHolder.getUserSession();
        if(user!=null){
            UserRatingMappingDTO userRatingMappingDTO = getUserRating(user.getId(),id).getData();
            if(userRatingMappingDTO != null) ratingDTO.setMyScore(userRatingMappingDTO.getScore());
        }
        return Result.ok(ratingDTO);
    }

    /**
     * 用户评分
     * @param urmd
     * @return
     */
    @PostMapping("/doRating")
    public Result doRating(@RequestBody UserRatingMappingDTO urmd){
        User user = UserHolder.getUserSession();
        urmd.setUserId(user.getId());
        return ratingService.doRating(urmd);
    }

    /**
     * 查看用户对应的评分
     * @param userId
     * @param ratingId
     * @return
     */
    @GetMapping("/{userId}/{ratingId}")
    public Result<UserRatingMappingDTO> getUserRating(@PathVariable("userId") Integer userId,
                                @PathVariable("ratingId") Integer ratingId){
        UserRatingMappingDTO userRatingMappingDTO = new UserRatingMappingDTO();
        BeanUtil.copyProperties(ratingService.getUserRating(userId,ratingId),userRatingMappingDTO);
        return Result.ok(userRatingMappingDTO);
    }
}
