package com.recipes.recipesApi.repository.custom.impl;

import com.recipes.recipesApi.model.entity.Ingredient;
import com.recipes.recipesApi.repository.custom.IngredientRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IngredientRepositoryCustomImpl implements IngredientRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ingredient> getByNames(Set<String> names) {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Ingredient.class);
        var ingredient = query.from(Ingredient.class);

        var namePath = ingredient.get("name");

        var predicates = new ArrayList<>();
        for (String name : names) {
            predicates.add(cb.equal(namePath, name));
        }
        query.select(ingredient)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
