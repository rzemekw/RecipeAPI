package com.recipes.recipesApi.repository.custom.impl;

import com.recipes.recipesApi.dto.RecipeIngredientDTO;
import com.recipes.recipesApi.model.entity.Recipe;
import com.recipes.recipesApi.model.entity.RecipeIngredient;
import com.recipes.recipesApi.repository.custom.RecipeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> getFilteredRecipeRows(Set<Long> ingredientIds, String title,
                                              String author, Integer skip, Integer take) {
        var query = entityManager.createQuery(
                "select r.id, r.author, r.title from Recipe r " +
                "join RecipeIngredient ri on r.id = ri.id.recipeId " +
                "where ri.id.ingredientId in :ingredientIds " +
                (title != null ? "and r.title like :title " : "") +
                (author != null ? "and r.author = :author " : "")
        );

        query.setParameter("ingredientIds", ingredientIds);
        if(title != null)
            query.setParameter("title", title + "%");
        if(author != null)
            query.setParameter("author", author);

        if(skip != null)
            query.setFirstResult(skip);
        query.setMaxResults(take);

        List<Object[]> result = query.getResultList();

        return result.stream().map(r -> {
            var recipe = new Recipe();
            recipe.setId((Long) r[0]);
            recipe.setAuthor((String) r[1]);
            recipe.setTitle((String) r[2]);
            return recipe;
        }).collect(Collectors.toList());
    }
}
