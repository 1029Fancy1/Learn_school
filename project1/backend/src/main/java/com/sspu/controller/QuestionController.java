package com.sspu.controller;

import com.sspu.entity.AnswerResponse;
import com.sspu.entity.Question;
import com.sspu.entity.QuestionRequest;
import com.sspu.service.AiService;
import com.sspu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AiService aiService;

    @PostMapping("/ask")
    public AnswerResponse askQuestion(@RequestBody QuestionRequest questionRequest) {
        // 保存问题
        Question question = new Question();
        question.setContent(questionRequest.getQuestion());
        question.setSessionId(questionRequest.getSession_id());
        question.setCreateTime(LocalDateTime.now());
        questionService.saveQuestion(question);

        // 调用AI服务获取答案
        return aiService.getAiAnswer(questionRequest);
    }

}