package com.capgemini.recipes.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class Recipe {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String servings;
    
    @NotNull
    private Integer cookTime;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy= "recipe")
    private List<Ingredient> ingredients = new ArrayList<>();

    protected void setIngredients(List<Ingredient> ingredient) {
        this.ingredients = ingredient;
    }

    public void addToIngredients(Ingredient ingredient) {
    	ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }
  
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdDate;
    
    @UpdateTimestamp
    private Date modifiedDate;

}
