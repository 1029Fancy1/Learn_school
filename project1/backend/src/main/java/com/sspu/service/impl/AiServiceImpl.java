package com.sspu.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sspu.entity.AnswerResponse;
import com.sspu.entity.QuestionRequest;
import com.sspu.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AiServiceImpl implements AiService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String AI_SERVICE_URL = "http://localhost:8000/api/ask";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AnswerResponse getAiAnswer(QuestionRequest questionRequest) {
        // 尝试从缓存中获取答案
        String cacheKey = "ai_answer:" + questionRequest.getQuestion();
        Object cachedObject = redisTemplate.opsForValue().get(cacheKey);
        if (cachedObject != null) {
            // 处理类型转换
            if (cachedObject instanceof AnswerResponse) {
                return (AnswerResponse) cachedObject;
            } else if (cachedObject instanceof Map) {
                // 将LinkedHashMap转换为AnswerResponse
                try {
                    return objectMapper.convertValue(cachedObject, AnswerResponse.class);
                } catch (Exception e) {
                    // 转换失败，继续调用AI服务
                    e.printStackTrace();
                }
            }
        }

        // 调用AI服务获取答案
        AnswerResponse answerResponse = restTemplate.postForObject(AI_SERVICE_URL, questionRequest, AnswerResponse.class);

        // 将答案缓存到Redis，过期时间1小时
        if (answerResponse != null) {
            redisTemplate.opsForValue().set(cacheKey, answerResponse, 1, TimeUnit.HOURS);
        }

        return answerResponse;
    }

}