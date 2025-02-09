package com.software.modsen.ratingservice.aspect;

import com.software.modsen.ratingservice.annotation.RedisCacheEvict;
import com.software.modsen.ratingservice.annotation.RedisCacheGet;
import com.software.modsen.ratingservice.annotation.RedisCachePut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import static com.software.modsen.ratingservice.util.RedisConstants.ARGUMENT_NOT_FOUND_EXCEPTION;
import static com.software.modsen.ratingservice.util.RedisConstants.DEFAULT_TIME_TO_LIVE_300;
import static com.software.modsen.ratingservice.util.RedisConstants.ILLEGAL_VALUE_ARGUMENT_EXCEPTION;
import static com.software.modsen.ratingservice.util.RedisConstants.PREFIX_RESULT;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisCacheAspect {
    public final RedisTemplate<Object, Object> redisTemplate;

    @Around("@annotation(redisCachePut)")
    public Object cacheMethod(ProceedingJoinPoint joinPoint, RedisCachePut redisCachePut) throws Throwable {
        Object result = joinPoint.proceed();
        String fullKey = buildKey(redisCachePut.value(), resolveKey(redisCachePut.key(), joinPoint, result));
        redisTemplate.opsForValue().set(fullKey, result, redisCachePut.ttl(), TimeUnit.SECONDS);
        return result;
    }

    @Around("@annotation(redisCacheGet)")
    public Object getFromCache(ProceedingJoinPoint joinPoint, RedisCacheGet redisCacheGet) throws Throwable {
        String fullKey = buildKey(redisCacheGet.value(), resolveKey(redisCacheGet.key(), joinPoint, null));
        Object result = redisTemplate.opsForValue().get(fullKey);
        if (result == null){
            result = joinPoint.proceed();
            redisTemplate.opsForValue().set(fullKey, result, DEFAULT_TIME_TO_LIVE_300, TimeUnit.SECONDS);
        }
        return result;
    }

    @Around("@annotation(redisCacheEvict)")
    public Object evictCache(ProceedingJoinPoint joinPoint, RedisCacheEvict redisCacheEvict) throws Throwable {
        String fullKey = buildKey(redisCacheEvict.value(), resolveKey(redisCacheEvict.key(), joinPoint, null));
        redisTemplate.delete(fullKey);
        return joinPoint.proceed();
    }

    private String buildKey(String namespace, String key) {
        return namespace + ":" + key;
    }

    private String resolveField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(object);
        if (value == null) {
            throw new IllegalArgumentException(ILLEGAL_VALUE_ARGUMENT_EXCEPTION);
        }
        return value.toString();
    }

    private String resolveKeyByParameterNameOfResult(String keyExpression, Object result) throws Throwable {
        String fieldName = keyExpression.substring(7);
        if (result == null) {
            throw new IllegalArgumentException(ILLEGAL_VALUE_ARGUMENT_EXCEPTION);
        }
        return resolveField(result, fieldName);
    }

    private String resolveKeyByParameterNameFromMethod(String keyExpression, ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(keyExpression)) {
                return args[i].toString();
            }
        }
        throw new IllegalArgumentException(ARGUMENT_NOT_FOUND_EXCEPTION);
    }

    private String resolveKey(String keyExpression, ProceedingJoinPoint joinPoint, Object result) throws Throwable {
        if (!keyExpression.startsWith(PREFIX_RESULT)) {
            return resolveKeyByParameterNameFromMethod(keyExpression, joinPoint);
        }
        return resolveKeyByParameterNameOfResult(keyExpression, result);
    }
}
