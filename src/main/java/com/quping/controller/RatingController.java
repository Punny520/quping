package com.quping.controller;

import cn.hutool.core.bean.BeanUtil;
import com.quping.common.PageInfo;
import com.quping.common.PageResult;
import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 评分相关Controller
 * @author: Punny
 * @date: 2024/9/10 21:25
 */
@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;
    @Autowired
    RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }
    /**
     * 插入评分
     * @param ratingDTO
     * @return
     */
    @PostMapping("/add")
    public Result<Void> addRating(@RequestBody RatingDTO ratingDTO){
        //TODO 参数验证
        return ratingService.insert(ratingDTO);
    }

    /**
     * 用户创建新评分
     * @param ratingDTO
     * @return
     */
    @PostMapping("/create")
    public Result<Void> createRating(@ModelAttribute RatingDTO ratingDTO){
        //TODO 参数验证
        return ratingService.create(ratingDTO);
    }

    /**
     * 根据id获取评分
     * @param id
     * @return
     */
    @GetMapping("/show/{id}")
    public Result<RatingDTO> getRating(@PathVariable Long id){
        return ratingService.showById(id);
    }

    /**
     * 用户评分
     * 使用新方案
     * @param urmd
     * @return
     */
    @PostMapping("/doRating")
    public Result<Void> doRating(@RequestBody UserRatingMappingDTO urmd){
        return ratingService.doRating(urmd);
    }

    /**
     * 查看用户对应的评分
     * @param userId
     * @param ratingId
     * @return
     */
    @GetMapping("/show/{userId}/{ratingId}")
    public Result<UserRatingMappingDTO> getUserRating(@PathVariable("userId") Long userId,
                                @PathVariable("ratingId") Long ratingId){
        UserRatingMappingDTO userRatingMappingDTO = new UserRatingMappingDTO();
        BeanUtil.copyProperties(ratingService.getUserRating(userId,ratingId),userRatingMappingDTO);
        return Result.ok(userRatingMappingDTO);
    }
    /**
     * 条件分页查询
     * @param pageInfo
     * @return
     */
    @PostMapping("/search")
    public Result<PageResult<RatingDTO>> search(@RequestBody PageInfo pageInfo){
        return ratingService.search(pageInfo);
    }
}
