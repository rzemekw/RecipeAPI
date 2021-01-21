package com.recipes.recipesApi.repository;

import com.recipes.recipesApi.model.entity.Recipe;
import com.recipes.recipesApi.repository.custom.RecipeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {
}