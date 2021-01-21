package com.recipes.recipesApi.api;

import com.recipes.recipesApi.dto.RecipeDTO;
import com.recipes.recipesApi.dto.RecipeFilterDTO;
import com.recipes.recipesApi.dto.RecipeRowDTO;
import com.recipes.recipesApi.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable long id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }

    @PostMapping("")
    public ResponseEntity<RecipeDTO> addRecipe(@RequestBody RecipeDTO recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }

    @PostMapping("/filter")
    public ResponseEntity<Set<RecipeRowDTO>> addRecipe(@RequestBody RecipeFilterDTO filter) {
        return ResponseEntity.ok(recipeService.getRecipeRows(filter));
    }



}
