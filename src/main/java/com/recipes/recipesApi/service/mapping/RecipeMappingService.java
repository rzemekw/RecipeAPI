package com.recipes.recipesApi.service.mapping;

import com.recipes.recipesApi.dto.RecipeDTO;
import com.recipes.recipesApi.dto.RecipeIngredientDTO;
import com.recipes.recipesApi.dto.RecipeRowDTO;
import com.recipes.recipesApi.model.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RecipeMappingService {

    private final IngredientMappingService ingredientMappingService;

    @Autowired
    public RecipeMappingService(IngredientMappingService ingredientMappingService) {
        this.ingredientMappingService = ingredientMappingService;
    }

    public RecipeDTO mapToRecipeDto(Recipe recipe) {
        var result = new RecipeDTO();
        result.setId(recipe.getId());
        result.setAuthor(recipe.getAuthor());
        result.setContent(recipe.getContent());
        result.setTitle(recipe.getTitle());
        result.setRecipeIngredients(recipe.getRecipeIngredients().stream()
                .map(ri -> {
                    var riDTO = new RecipeIngredientDTO();
                    riDTO.setIngredient(ingredientMappingService.mapToIngredientDTO(ri.getIngredient()));
                    riDTO.setQuantity(ri.getQuantity());
                    return riDTO;
                })
                .collect(Collectors.toSet()));

        return result;
    }

    public Recipe mapToRecipe(RecipeDTO recipe) {
        var result = new Recipe();
        result.setAuthor(recipe.getAuthor());
        result.setContent(recipe.getContent());
        result.setTitle(recipe.getTitle());

        return result;
    }

    public RecipeRowDTO mapToRecipeRowDTO(Recipe recipe) {
        var result = new RecipeRowDTO();
        result.setAuthor(recipe.getAuthor());
        result.setTitle(recipe.getTitle());
        result.setId(recipe.getId());

        return result;
    }
}
