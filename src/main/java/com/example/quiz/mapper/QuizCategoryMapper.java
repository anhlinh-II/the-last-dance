package com.example.quiz.mapper;

import com.example.quiz.base.baseInterface.BaseMapstruct;
import com.example.quiz.model.dto.response.QuizCategoryResponse;
import com.example.quiz.model.dto.resquest.QuizCategoryRequest;
import com.example.quiz.model.entity.QuizCategory;
import com.example.quiz.model.view.QuizCategoryView;
import org.mapstruct.Mapper;

@Mapper
public interface QuizCategoryMapper extends BaseMapstruct<QuizCategory, QuizCategoryRequest, QuizCategoryResponse, QuizCategoryView> {

}
