package com.balkis.quiz.controllers;



import com.balkis.quiz.models.QuizQuestion;
import com.balkis.quiz.models.User;
import com.balkis.quiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;



    @PostMapping("/add")
    public ResponseEntity<QuizQuestion> addQuestion(@RequestBody QuizQuestion question) {
        QuizQuestion savedQuestion = quizService.addQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    }

    // Endpoint pour récupérer les questions du quiz
    @GetMapping("/questions")
    public List<QuizQuestion> getQuizQuestions() {
        return quizService.getQuizQuestions();
    }

    // Endpoint pour l'utilisateur de répondre au quiz
    @PostMapping("/submit")
    public String submitQuiz(@RequestBody User user) {
        // Ici, tu peux enregistrer le score de l'utilisateur
        // Par exemple, on peut utiliser un service pour mettre à jour le score de l'utilisateur
        // Pour l'instant, on renvoie un message simple
        return "Score de " + user.getUsername() + " enregistré avec succès!";
    }
}
