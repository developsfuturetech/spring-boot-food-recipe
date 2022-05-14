package com.capgemini.recipes.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.recipes.dto.RecipeDto;
import com.capgemini.recipes.exceptions.ResourceNotFoundException;
import com.capgemini.recipes.model.Ingredient;
import com.capgemini.recipes.model.Recipe;
import com.capgemini.recipes.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<RecipeDto> getAllRecipes() {
		log.debug(":::::::In getAllRecipes method :::::::::::");
		return recipeRepository.findAll()
				.stream().map(this::convertEntityToDto).collect(toList());
	}

	public RecipeDto findById(Long id) {
		log.debug(":::::::In findById method :::::::::::");
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Recipe with ID :"+id+" Not Found!"));
		return convertEntityToDto(recipe);
	}

	public void save(RecipeDto recipeDto) {
		log.debug(":::::::In save method :::::::::::");

		List<Ingredient> ingredientList=recipeDto.getIngredients().stream().map(t -> {return modelMapper.map(t, Ingredient.class);
		}).collect(Collectors.toList());

		Recipe recipe=modelMapper.map(recipeDto, Recipe.class);
		for(Ingredient ingredients: ingredientList ) {
			recipe.addToIngredients(ingredients);
		}
		recipeRepository.saveAndFlush(recipe);
	}

	public void update(RecipeDto recipeDto, Long id) {
		log.debug(":::::::In update method :::::::::::");

		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Recipe with ID :"+id+" Not Found!"));
		List<Ingredient> ingredientList = recipe.getIngredients();

		recipe.setCookTime(recipeDto.getCooktime());
		recipe.setName(recipeDto.getName());
		recipe.setServings(recipeDto.getServings());

		for(int index=0;index<ingredientList.size();index++) {
			ingredientList.get(index).setName(recipeDto.getIngredients().get(index).getName());
			ingredientList.get(index).setQuantity(recipeDto.getIngredients().get(index).getQuantity());
		}
		recipeRepository.saveAndFlush(recipe);
	}

	public void delete(Long id) {
		log.debug(":::::::In delete method :::::::::::");
		recipeRepository.findById(id)
		.orElseThrow(()->new ResourceNotFoundException("Recipe with ID :"+id+" Not Found!"));
		recipeRepository.deleteById(id);
	}

	private RecipeDto convertEntityToDto(Recipe recipe) {
		log.debug(":::::::In convertEntityToDto method :::::::::::");
		return modelMapper.map(recipe, RecipeDto.class);
	}
}
