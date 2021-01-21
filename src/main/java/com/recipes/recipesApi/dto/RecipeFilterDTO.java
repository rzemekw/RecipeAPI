package com.recipes.recipesApi.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RecipeFilterDTO {
    private Set<Long> ingredientIds;
    private Set<String> ingredientNames;
    private String author;
    private String title;
    private Integer skip;
    private Integer take;
}
