package com.capgemini.recipes.service;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	
	@Autowired
	private ModelMapper modelMapper;

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

		List<Ingredients> ingredientList=recipeDto.getIngredients().stream().map(t -> {return modelMapper.map(t, Ingredients.class);
			}).collect(Collectors.toList());
	
		Recipe recipe=modelMapper.map(recipeDto, Recipe.class);
		for(Ingredients ingredients: ingredientList ) {
			ingredients.setCreatedDate(Instant.now());
			recipe.addToIngredients(ingredients);
		}
		recipe.setCreatedDate(Instant.now());
		recipeRepository.saveAndFlush(recipe);
	}

	public void delete(Long id) {
		recipeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Recipe with ID :"+id+" Not Found!"));
		recipeRepository.deleteById(id);
	}

	private RecipeDto convertEntityToDto(Recipe recipe) {

		return modelMapper.map(recipe, RecipeDto.class);
	}
}
