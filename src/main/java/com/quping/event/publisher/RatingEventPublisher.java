package com.quping.event.publisher;

import com.quping.event.RatingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RatingEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    public RatingEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    public void publishEventWithDelay(Long id) {
        log.info("[{}]:1s后将发送消息:", LocalDateTime.now());
        scheduler.schedule(() -> {
            RatingEvent event = new RatingEvent(this,id);
            eventPublisher.publishEvent(event);
        }, 1, TimeUnit.SECONDS); // 延迟1秒发布
    }
}
