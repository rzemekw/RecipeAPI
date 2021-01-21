package com.recipes.recipesApi.repository;

import com.recipes.recipesApi.model.entity.Ingredient;
import com.recipes.recipesApi.model.entity.Recipe;
import com.recipes.recipesApi.repository.custom.IngredientRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepositoryCustom {

}
