package com.balkis.quiz.models;




import jakarta.persistence.*;

@Entity
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String choiceText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public QuizQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QuizQuestion question) {
        this.question = question;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizquestion_id")
    private QuizQuestion question;

    // Getters and Setters
}

