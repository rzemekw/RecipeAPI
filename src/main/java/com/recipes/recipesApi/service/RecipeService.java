package com.recipes.recipesApi.service;

import com.recipes.recipesApi.dto.RecipeDTO;
import com.recipes.recipesApi.dto.RecipeFilterDTO;
import com.recipes.recipesApi.dto.RecipeRowDTO;

import java.util.Set;

public interface RecipeService {
    RecipeDTO getRecipe(long id);

    RecipeDTO addRecipe(RecipeDTO recipeDTO);

    Set<RecipeRowDTO> getRecipeRows(RecipeFilterDTO filter);
}
