package com.balkis.quiz.repositories;


import com.balkis.quiz.models.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuizQuestion, Long> {
}
