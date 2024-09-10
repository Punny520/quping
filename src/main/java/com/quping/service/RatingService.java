package com.quping.service;


import com.quping.common.Result;
import com.quping.dto.RatingDTO;

public interface RatingService {
    Result insert(RatingDTO ratingDTO);
}
