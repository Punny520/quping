package com.quping.event;

import com.quping.entity.Rating;
import org.springframework.context.ApplicationEvent;

public class RatingEvent extends ApplicationEvent {
    private final Long ratingId;
    public RatingEvent(Object source, Long ratingId) {
        super(source);
        this.ratingId = ratingId;
    }
    public Long getRatingId() {
        return ratingId;
    }
}
