package com.capgemini.recipes.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecipeDto {
	
   @ApiModelProperty(hidden = true)
	private Long id;
	@NotEmpty
	@Size(min = 3, message = "Recipe Name should have at least 3 characters")
	private String name;
	@NotEmpty
	@Size(min = 1, message = "Recipe Servings should have at least 1")
	private String servings;
	@NotNull
	private Integer cooktime;

	private List<IngredientDto> ingredients = new ArrayList<>();
}
