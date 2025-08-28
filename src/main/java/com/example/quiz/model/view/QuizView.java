package com.example.quiz.model.view;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.time.LocalDateTime;

@Immutable
@Entity
@Subselect("""
    SELECT 
        q.id,
        q.title,
        q.description,
        q.time_limit AS timeLimit,
        q.is_active AS isActive,
        q.difficulty_level AS difficultyLevel,
        q.cover_image_url AS coverImageUrl,
        q.created_at AS createdAt,
        q.updated_at AS updatedAt,
        qc.id AS categoryId,
        qc.name AS categoryName,
        qc.image_url AS categoryImageUrl,
    FROM 
        quizzes q
    JOIN 
        quiz_categories qc ON q.category_id = qc.id
""")
@Synchronize({"quizzes", "quiz_categories", "questions", "quiz_attempts"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizView {

    @Id
    private Long id;

    private String title;
    private String description;
    private Integer timeLimit;
    private Boolean isActive;
    private Integer difficultyLevel;
    private String coverImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Category info
    private Long categoryId;
    private String categoryName;
    private String categoryImageUrl;

    // Statistics
    private Integer questionCount;
    private Integer attemptCount;
}
