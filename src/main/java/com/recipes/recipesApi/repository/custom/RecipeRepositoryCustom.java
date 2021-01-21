package com.recipes.recipesApi.repository.custom;

import com.recipes.recipesApi.model.entity.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeRepositoryCustom {
    List<Recipe> getFilteredRecipeRows(Set<Long> ingredientIds, String title, String author, Integer skip, Integer take);
}
