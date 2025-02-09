package com.software.modsen.ratingservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.software.modsen.ratingservice.util.RedisConstants.DEFAULT_TIME_TO_LIVE_300;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisCachePut {
    String value();
    String key();
    long ttl() default DEFAULT_TIME_TO_LIVE_300;
}
