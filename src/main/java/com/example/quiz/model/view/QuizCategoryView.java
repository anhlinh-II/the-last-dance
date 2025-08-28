package com.example.quiz.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import net.jcip.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Immutable
@Subselect("""
    SELECT 
        qc.id,
        qc.name,
        qc.description,
        qc.image_url as imageUrl,
        COUNT(q.id) as quiz_count
    FROM 
        quiz_categories qc
    LEFT JOIN 
        quizzes q ON q.category_id = qc.id
    GROUP BY 
        qc.id
""")
@Entity
@Getter
@Setter
public class QuizCategoryView {
    @Id
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long quizCount;
}
