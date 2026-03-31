package com.sspu.service.impl;

import com.sspu.entity.Question;
import com.sspu.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Override
    public Question saveQuestion(Question question) {
        // 这里可以实现保存问题到数据库的逻辑
        // 暂时返回传入的问题对象
        return question;
    }

    @Override
    public Question getQuestionById(Long id) {
        // 这里可以实现根据ID查询问题的逻辑
        // 暂时返回null
        return null;
    }

}