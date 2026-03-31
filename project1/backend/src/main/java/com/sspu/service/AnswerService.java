package com.sspu.service;

import com.sspu.entity.Answer;

public interface AnswerService {
    Answer saveAnswer(Answer answer);
    Answer getAnswerByQuestionId(Long questionId);
}