package com.balkis.quiz.services;

import com.balkis.quiz.models.Category;
import com.balkis.quiz.models.CategoryResponse;
import com.balkis.quiz.models.QuizQuestion;
import com.balkis.quiz.models.QuizResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryService {
    private static final String QUESTIONS_API_URL = "https://opentdb.com/api.php?amount=10&type=multiple&category=";
    private static final int MAX_RETRIES = 5;
        private static final String CATEGORY_API_URL = "https://opentdb.com/api_category.php";

    @Autowired
    private RestTemplate restTemplate;

    public List<Category> getCategories() {
        try {
            // Envoie une requête GET et récupère la réponse brute
            ResponseEntity<String> response = restTemplate.getForEntity(CATEGORY_API_URL, String.class);

            // Affiche la réponse brute dans les logs
            System.out.println("Réponse brute de l'API : " + response.getBody());

            // Vérifie si la réponse n'est pas vide
            if (response.getBody() != null && !response.getBody().isEmpty()) {
                // Convertit la réponse en objet (utilise un parseur JSON)
                ObjectMapper objectMapper = new ObjectMapper();
                CategoryResponse categoryResponse = objectMapper.readValue(response.getBody(), CategoryResponse.class);

                if (categoryResponse != null && categoryResponse.getTriviaCategories() != null) {
                    return categoryResponse.getTriviaCategories();
                } else {
                    throw new RuntimeException("Réponse mal formée ou vide dans la structure JSON");
                }
            } else {
                throw new RuntimeException("Réponse vide de l'API externe");
            }
        } catch (Exception e) {
            // Capture l'exception et affiche un message d'erreur plus détaillé
            System.err.println("Erreur lors de la récupération des catégories : " + e.getMessage());
            throw new RuntimeException("Erreur interne lors de la récupération des catégories", e);
        }
    }

    public List<QuizQuestion> getQuestionsByCategory(int categoryId, int amount) {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                // Construction de l'URL avec l'ID de la catégorie et le nombre de questions
                String url = QUESTIONS_API_URL + categoryId + "&amount=" + amount;
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                if (response.getBody() != null && !response.getBody().isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    QuizResponse questionResponse = objectMapper.readValue(response.getBody(), QuizResponse.class);

                    if (questionResponse != null && questionResponse.getResults() != null) {
                        return questionResponse.getResults();
                    } else {
                        throw new RuntimeException("Réponse mal formée ou vide dans la structure JSON des questions");
                    }
                } else {
                    throw new RuntimeException("Réponse vide de l'API externe pour les questions");
                }
            } catch (Exception e) {
                // Vérifie si c'est une erreur "429 Too Many Requests"
                if (e instanceof org.springframework.web.client.HttpClientErrorException.TooManyRequests) {
                    System.err.println("Erreur 429 : Trop de requêtes, tentative " + (attempt + 1) + "...");
                    attempt++;
                    try {
                        // Attente avant la nouvelle tentative
                        TimeUnit.SECONDS.sleep(10); // Attendre 10 secondes
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.err.println("Erreur lors de la récupération des questions : " + e.getMessage());
                    throw new RuntimeException("Erreur interne lors de la récupération des questions", e);
                }
            }
        }

        throw new RuntimeException("Nombre maximum de tentatives atteint après " + MAX_RETRIES + " tentatives");
    }
    }








