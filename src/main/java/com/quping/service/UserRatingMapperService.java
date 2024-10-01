package com.quping.service;

import com.quping.entry.UserRatingMapping;

public interface UserRatingMapperService {
    UserRatingMapping getByEntry(UserRatingMapping userRatingMapping);
}
