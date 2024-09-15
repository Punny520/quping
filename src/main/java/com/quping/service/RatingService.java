package com.quping.service;


import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;

public interface RatingService {
    Result insert(RatingDTO ratingDTO);

    Result getById(int id);

    Result doRating(UserRatingMappingDTO urmd);
}
