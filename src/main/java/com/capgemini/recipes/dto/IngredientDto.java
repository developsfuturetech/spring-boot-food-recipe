package com.capgemini.recipes.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
	
	@JsonIgnore
	private Long id;
	
	@NotEmpty
	@Size(min = 3, message = "Ingredient Name should be at least 3 characters")
	private String name;
	
	@NotEmpty
	private String quantity;
	
}
