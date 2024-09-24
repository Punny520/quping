package com.quping.service;


import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entry.Rating;

public interface RatingService {
    Result insert(RatingDTO ratingDTO);

    Result<Rating> getById(int id);

    Result doRating(UserRatingMappingDTO urmd);
}
