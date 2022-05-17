package com.capgemini.recipes.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
	
	@JsonIgnore
	private Long id;
	
	@NotEmpty(message= "Recipe Name cannot be empty")
	@Size(min = 3, message = "Recipe Name should have at least 3 characters")
	private String name;
	
	@NotEmpty(message= "Category cannot be empty")
	private String category;
	
	@NotEmpty(message= "Servings cannot be empty")
	@Size(min = 1, message = "Recipe Servings should have at least 1")
	private String servings;
	
	@NotEmpty(message= "Cooktime cannot be empty")
	private String cooktime;

	private List<IngredientDto> ingredients = new ArrayList<>();
}
