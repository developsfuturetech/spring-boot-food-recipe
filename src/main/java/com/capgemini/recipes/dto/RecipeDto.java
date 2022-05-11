package com.capgemini.recipes.dto;

import java.util.ArrayList;
import java.util.List;

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
public class RecipeDto {
	
   @ApiModelProperty(hidden = true)
	private Long id;
	@NotEmpty
	@Size(min = 5, message = "recipe name should have at least 5 characters")
	private String name;
	private String servings;
	private Integer cooktime;
	private Integer calories;
    private List<IngredientDto> ingredients = new ArrayList<IngredientDto>();
}
