package com.example.quiz.model.dto.response;

import lombok.Data;

@Data
public class QuizCategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long quizCount;
}
