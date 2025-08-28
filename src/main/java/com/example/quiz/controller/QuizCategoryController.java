package com.example.quiz.controller;

import com.example.quiz.base.impl.BaseController;
import com.example.quiz.model.dto.request.QuizCategoryRequest;
import com.example.quiz.model.dto.response.QuizCategoryResponse;
import com.example.quiz.model.entity.QuizCategory;
import com.example.quiz.model.view.QuizCategoryView;
import com.example.quiz.service.QuizCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz-categories")
@RequiredArgsConstructor
public class QuizCategoryController extends BaseController<QuizCategory, Long, QuizCategoryRequest, QuizCategoryResponse, QuizCategoryView> {

    private final QuizCategoryService quizCategoryService;

    @Override
    protected QuizCategoryService getService() {
        return quizCategoryService;
    }
}
