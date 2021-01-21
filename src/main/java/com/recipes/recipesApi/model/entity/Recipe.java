package com.recipes.recipesApi.model.entity;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipe")
@Getter
@Setter
public class Recipe {
    public static Recipe EMPTY = new Recipe();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "author")
    private String author;

    @JsonIgnore
    @OneToMany(mappedBy="recipe", fetch = FetchType.LAZY)
    private Set<RecipeIngredient> recipeIngredients;
}
