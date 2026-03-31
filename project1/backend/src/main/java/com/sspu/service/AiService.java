package com.sspu.service;

import com.sspu.entity.AnswerResponse;
import com.sspu.entity.QuestionRequest;

public interface AiService {
    AnswerResponse getAiAnswer(QuestionRequest questionRequest);
}