package com.recipes.recipesApi.service.mapping;

import com.recipes.recipesApi.dto.IngredientDTO;
import com.recipes.recipesApi.dto.RecipeDTO;
import com.recipes.recipesApi.dto.RecipeIngredientDTO;
import com.recipes.recipesApi.model.entity.Ingredient;
import com.recipes.recipesApi.model.entity.Recipe;
import org.springframework.stereotype.Service;

@Service
public class IngredientMappingService {
    public IngredientDTO mapToIngredientDTO(Ingredient ingredient) {
        var result = new IngredientDTO();
        result.setId(ingredient.getId());
        result.setName(ingredient.getName());
        return result;
    }

    public Ingredient mapToIngredient(IngredientDTO ingredient) {
        var result = new Ingredient();
        result.setId(ingredient.getId());
        result.setName(ingredient.getName());
        return result;
    }

}

