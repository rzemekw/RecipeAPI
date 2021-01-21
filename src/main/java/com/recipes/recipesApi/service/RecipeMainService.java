package com.recipes.recipesApi.service;

import com.recipes.recipesApi.dto.RecipeDTO;
import com.recipes.recipesApi.dto.RecipeFilterDTO;
import com.recipes.recipesApi.dto.RecipeRowDTO;
import com.recipes.recipesApi.model.entity.Ingredient;
import com.recipes.recipesApi.model.entity.RecipeIngredient;
import com.recipes.recipesApi.model.entity.RecipeIngredientKey;
import com.recipes.recipesApi.repository.IngredientRepository;
import com.recipes.recipesApi.repository.RecipeIngredientRepository;
import com.recipes.recipesApi.repository.RecipeRepository;
import com.recipes.recipesApi.service.mapping.RecipeMappingService;
import constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecipeMainService implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeMappingService mappingService;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeMainService(RecipeRepository recipeRepository,
                             RecipeMappingService mappingService,
                             IngredientRepository ingredientRepository,
                             RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeRepository = recipeRepository;
        this.mappingService = mappingService;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public RecipeDTO getRecipe(long id) {
        return mappingService.mapToRecipeDto(recipeRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        var result = recipeRepository.save(mappingService.mapToRecipe(recipeDTO));
        recipeDTO.setId(result.getId());
        var recipeIngredients = recipeDTO.getRecipeIngredients();
        var prevIngredients = ingredientRepository.getByNames(recipeDTO.getRecipeIngredients().stream()
                .map(ri -> ri.getIngredient().getName()).collect(Collectors.toSet()));
        var prevIngredientNames = prevIngredients.stream()
                .map(i -> i.getName())
                .collect(Collectors.toCollection(HashSet::new));

        var newIngredientToSave = recipeIngredients.stream()
                .filter(ri -> !prevIngredientNames.contains(ri.getIngredient().getName()))
                .map(ri -> {
                    var ingredient = new Ingredient();
                    ingredient.setName(ri.getIngredient().getName());
                    return ingredient;
                })
                .collect(Collectors.toList());

        var newIngredients = ingredientRepository.saveAll(newIngredientToSave);

        var recipeIngredientsToSave = new HashSet<RecipeIngredient>();
        var ingredientsNamesQuantity = new HashMap<String, String>();
        for(var ri : recipeIngredients) {
            ingredientsNamesQuantity.put(ri.getIngredient().getName(), ri.getQuantity());
        }
        Stream.concat(prevIngredients.stream(), newIngredients.stream()).forEach(i -> {
            var ri = new RecipeIngredient();
            var id = new RecipeIngredientKey();
            ri.setId(id);
            id.setRecipeId(result.getId());
            id.setIngredientId(i.getId());
            ri.setIngredient(i);
            ri.setRecipe(result);
            ri.setQuantity(ingredientsNamesQuantity.get(i.getName()));
            recipeIngredientsToSave.add(ri);
        });

        recipeIngredientRepository.saveAll(recipeIngredientsToSave);

        return recipeDTO;
    }

    @Override
    public Set<RecipeRowDTO> getRecipeRows(RecipeFilterDTO filter) {

        if(filter.getTake() == null || filter.getTake() > Constants.MAX_TAKE) {
            filter.setTake(Constants.MAX_TAKE);
        }

        Set<Long> ingredientIds;
        if(filter.getIngredientNames() != null) {
            var ingredientsToAdd = ingredientRepository.getByNames(filter.getIngredientNames());
            if(filter.getIngredientIds() != null) {
                ingredientIds = Stream.concat(filter.getIngredientIds().stream(),
                        ingredientsToAdd.stream().map(i -> i.getId()))
                        .collect(Collectors.toSet());
            }
            else {
                ingredientIds = ingredientsToAdd.stream().map(i -> i.getId()).collect(Collectors.toSet());
            }
        }
        else {
            ingredientIds = new HashSet<>();
        }

        var recipes = recipeRepository.getFilteredRecipeRows(ingredientIds, filter.getTitle(),
                filter.getAuthor(), filter.getSkip(), filter.getTake());

        return recipes.stream().map(r -> mappingService.mapToRecipeRowDTO(r)).collect(Collectors.toSet());
    }
}
