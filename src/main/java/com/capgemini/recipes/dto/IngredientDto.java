package com.capgemini.recipes.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IngredientDto {
	
   @ApiModelProperty(hidden = true)
	private Long id;
	@NotEmpty
	@Size(min = 3, message = "Ingredient Name should be at least 3 characters")
	private String name;
	@NotNull
	private Integer quantity;

}
