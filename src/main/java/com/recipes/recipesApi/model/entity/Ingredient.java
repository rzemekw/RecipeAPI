package com.recipes.recipesApi.model.entity;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "ingredient", indexes = {@Index(name = "nameInd", columnList = "name", unique = true)})
@Getter
@Setter
public class Ingredient implements Serializable {

    public static Ingredient EMPTY = new Ingredient();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy="ingredient")
    private Set<RecipeIngredient> recipeIngredients;
}