package com.capgemini.recipes.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String servings;
    
    @NotBlank
    private Integer cookTime;
    
    @NotBlank
    private Integer calories;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy= "recipe")
    private List<Ingredients> ingredients = new ArrayList<Ingredients>();
  
    private Instant createdDate;
    
    private Instant modifiedDate;

}
