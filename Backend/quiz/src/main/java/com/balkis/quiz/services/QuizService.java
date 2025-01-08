package com.balkis.quiz.services;

import com.balkis.quiz.models.QuizQuestion;
import com.balkis.quiz.models.QuizResponse;
import com.balkis.quiz.repositories.QuestionRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class QuizService {
    private final QuestionRepository questionRepository;

    private static final String API_URL = "https://opentdb.com/api.php?amount=10&category=9&difficulty=medium&type=multiple";

    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public QuizQuestion addQuestion(QuizQuestion question) {
        return questionRepository.save(question);
    }
    public List<QuizQuestion> getQuizQuestions() {
        RestTemplate restTemplate = new RestTemplate();

        // Envoie une requête GET pour récupérer les données de l'API
        ResponseEntity<QuizResponse> response = restTemplate.exchange(API_URL, HttpMethod.GET, null, QuizResponse.class);

        // Récupère et retourne les questions depuis la réponse de l'API
        return response.getBody() != null ? response.getBody().getResults() : null;
    }
}
