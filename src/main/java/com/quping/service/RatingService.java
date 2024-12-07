package com.quping.service;


import com.quping.common.PageInfo;
import com.quping.common.PageResult;
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

    Result<RatingDTO> showById(Long id);

    Result<PageResult<RatingDTO>> search(PageInfo pageInfo);
}
