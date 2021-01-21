package com.recipes.recipesApi.repository.custom;

import com.recipes.recipesApi.model.entity.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientRepositoryCustom {
    List<Ingredient> getByNames(Set<String> names);
}
