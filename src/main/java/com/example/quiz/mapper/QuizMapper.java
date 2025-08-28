package com.example.quiz.mapper;

import com.example.quiz.base.baseInterface.BaseMapstruct;
import com.example.quiz.model.dto.response.QuizResponse;
import com.example.quiz.model.dto.resquest.QuizRequest;
import com.example.quiz.model.entity.Quiz;
import com.example.quiz.model.view.QuizView;
import org.mapstruct.Mapper;

@Mapper
public interface QuizMapper extends BaseMapstruct<Quiz, QuizRequest, QuizResponse, QuizView> {
}
