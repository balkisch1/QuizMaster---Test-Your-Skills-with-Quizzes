package com.balkis.quiz.models;

import com.balkis.quiz.models.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CategoryResponse {

    @JsonProperty("trivia_categories")
    private List<Category> triviaCategories;

    public List<Category> getTriviaCategories() {
        return triviaCategories;
    }

    public void setTriviaCategories(List<Category> triviaCategories) {
        this.triviaCategories = triviaCategories;
    }
}
