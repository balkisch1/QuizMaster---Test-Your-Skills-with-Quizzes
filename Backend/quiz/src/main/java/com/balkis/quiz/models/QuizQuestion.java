package com.balkis.quiz.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.util.List;

@Entity
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("question") // Correspond au nom du champ dans la réponse JSON
    private String question;

    @JsonProperty("correct_answer") // Mappage explicite du champ JSON
    private String correctAnswer;

    @ElementCollection
    @JsonProperty("incorrect_answers") // Idem pour incorrect_answers
    private List<String> incorrectAnswers;

    @JsonProperty("type")
    private String type;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonProperty("category")
    private String category;

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Choice> choices;

}

