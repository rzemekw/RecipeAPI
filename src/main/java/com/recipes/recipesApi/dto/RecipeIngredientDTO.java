package com.recipes.recipesApi.dto;

import lombok.Data;

@Data
public class RecipeIngredientDTO {
    private IngredientDTO ingredient;
    private String quantity;
}
