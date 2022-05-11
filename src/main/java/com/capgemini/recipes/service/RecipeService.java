package com.capgemini.recipes.service;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.recipes.dto.IngredientDto;
import com.capgemini.recipes.dto.RecipeDto;
import com.capgemini.recipes.exceptions.ResourceNotFoundException;
import com.capgemini.recipes.model.Ingredients;
import com.capgemini.recipes.model.Recipe;
import com.capgemini.recipes.repository.RecipeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	public List<RecipeDto> getAllRecipes() {
		return recipeRepository.findAll()
				.stream().map(this::convertEntityToDto).collect(toList());
	}

	public RecipeDto findById(Long id) {
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Recipe with ID :"+id+" Not Found!"));
		return convertEntityToDto(recipe);
	}

	public void save(RecipeDto recipeDto) {
		Recipe recipe=new Recipe();

		//  recipe.setIngredients(recipeDto.getIngredients());
		recipe.setName(recipeDto.getName());
		recipe.setCookTime(recipeDto.getCooktime());
		recipe.setServings(recipeDto.getServings());
		recipe.setCalories(recipeDto.getCalories());
		recipe.setCreatedDate(Instant.now());
		recipeRepository.save(recipe);

	}

	public void delete(Long id) {
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Recipe with ID :"+id+" Not Found!"));
		recipeRepository.deleteById(id);
	}

	private RecipeDto convertEntityToDto(Recipe recipe) {
		return RecipeDto.builder().id(recipe.getId())
				.name(recipe.getName())
				.calories(recipe.getCalories())
				.cooktime(recipe.getCookTime())
				.ingredients(convertIngredientEntityToDto(recipe.getIngredients()))
				.servings(recipe.getServings())
				.build();
	}

	private List<IngredientDto> convertIngredientEntityToDto(List<Ingredients> ingredientList) {

		List<IngredientDto> ingredientDtoList = ingredientList.stream().map(t -> {return IngredientDto.builder().id(t.getId())
				.ingredientName(t.getName()) .quantity(t.getQuantity())
				.build();}).collect(Collectors.toList());

		return ingredientDtoList;

	}	





}
