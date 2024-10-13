package com.quping.service;


import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entry.Rating;
import com.quping.entry.UserRatingMapping;

public interface RatingService {
    Result<Void> insert(RatingDTO ratingDTO);

    Rating getById(int id);

    Result doRating(UserRatingMappingDTO urmd);

    UserRatingMapping getUserRating(Integer userId, Integer ratingId);
}
