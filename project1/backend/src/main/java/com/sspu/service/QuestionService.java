package com.sspu.service;

import com.sspu.entity.Question;

public interface QuestionService {
    Question saveQuestion(Question question);
    Question getQuestionById(Long id);
}