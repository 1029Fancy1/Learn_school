package com.sspu.entity;

import java.util.List;

public class AnswerResponse {
    private String answer;
    private List<String> image_urls;

    public AnswerResponse() {
    }

    public AnswerResponse(String answer, List<String> image_urls) {
        this.answer = answer;
        this.image_urls = image_urls;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }
}