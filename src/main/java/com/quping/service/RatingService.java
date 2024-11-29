package com.quping.service;


import com.quping.common.PageInfo;
import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entry.Rating;
import com.quping.entry.UserRatingMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RatingService {
    Result<Void> insert(RatingDTO ratingDTO);

    Rating getById(int id);

    Result<Void> doRating(UserRatingMappingDTO urmd);

    UserRatingMapping getUserRating(Integer userId, Integer ratingId);

    Result<Void> create(RatingDTO ratingDTO);

    Result<List<RatingDTO>> page(PageInfo pageInfo);
}
