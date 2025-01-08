package com.balkis.quiz.controllers;

import com.balkis.quiz.models.Category;
import com.balkis.quiz.models.QuizQuestion;
import com.balkis.quiz.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/categories")
    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<Category> categories = categoryService.getCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            // Capture l'exception et renvoie un message d'erreur plus précis
            System.err.println("Erreur interne lors de la récupération des catégories : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/api/categories/{categoryId}/questions")
    public List<QuizQuestion> getQuestionsByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "10") int amount) {

        return categoryService.getQuestionsByCategory(categoryId, amount);
    }
}
