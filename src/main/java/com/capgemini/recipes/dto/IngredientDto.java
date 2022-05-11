package com.capgemini.recipes.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDto {
	
   @ApiModelProperty(hidden = true)
	private Long id;
	@NotEmpty
	@Size(min = 5, message = "ingredient name should have at least 5 characters")
	private String ingredientName;
	@NotEmpty
	private Integer quantity;
}
