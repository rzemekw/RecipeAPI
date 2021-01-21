package com.recipes.recipesApi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class RecipeDTO {
    private Long id;
    private String title;
    private Set<RecipeIngredientDTO> recipeIngredients;
    private String content;
    private String author;
}