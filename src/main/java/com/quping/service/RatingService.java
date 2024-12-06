package com.quping.service;


import com.quping.common.PageInfo;
import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entity.Rating;
import com.quping.entity.UserRatingMapping;

import java.util.List;

public interface RatingService {
    Result<Void> insert(RatingDTO ratingDTO);

    Rating getById(long id);

    Result<Void> doRating(UserRatingMappingDTO urmd);

    UserRatingMapping getUserRating(Long userId, Long ratingId);

    Result<Void> create(RatingDTO ratingDTO);

    Result<List<RatingDTO>> page(PageInfo pageInfo);

    Long getTotal();

    Result<RatingDTO> showById(Long id);
}
