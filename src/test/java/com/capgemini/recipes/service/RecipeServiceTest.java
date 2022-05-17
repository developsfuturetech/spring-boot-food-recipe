package com.capgemini.recipes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.capgemini.recipes.dto.RecipeDto;
import com.capgemini.recipes.exceptions.ResourceNotFoundException;
import com.capgemini.recipes.model.Ingredient;
import com.capgemini.recipes.model.Recipe;
import com.capgemini.recipes.repository.RecipeRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)//for JUnit 5, but you can add the JUnit 4 annotation
class RecipeServiceTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	RecipeService recipeService;

	@Mock
	ModelMapper modelMapper = new ModelMapper();

	@Mock
	RecipeRepository recipeRepository;


	Ingredient ingredient_1 = new Ingredient(1l,"Biryani Rice", "2 Kgs");
	Ingredient ingredient_2 = new Ingredient(2l,"Chicken", "300 gms");
	List<Ingredient> list = Arrays.asList(ingredient_1,ingredient_2);



	@Test
	public void testGetRecipes() {

		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();

		recipe.addToIngredients(ingredient);

		List<Recipe> recipeData = new ArrayList<>();
		recipeData.add(recipe);

		when(recipeRepository.findAll()).thenReturn(recipeData);
		List<RecipeDto> recipes = recipeService.getAllRecipes();
		assertEquals(1, recipes.size());
		Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testGetRecipesById() {

		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		recipe.addToIngredients(ingredient);
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		RecipeDto recipes = recipeService.findById(anyLong());
		Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
	}
	
	@Test
	public void testRecipeByIdNotFound() {
		ResourceNotFoundException thrown = assertThrows(
				ResourceNotFoundException.class,
		           () -> recipeService.findById(123L),
		           "Expected findbyId() to throw");

	    assertTrue(thrown.getMessage().contains("Recipe with ID"));
	}
	
	@Test
	void deleteById() {
	    Recipe recipe = new Recipe();
	    when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
	    recipeService.delete(1L);
	    verify(recipeRepository).findById(1L);
	}
	
	@Test
	void deleteActorByIdThrowsExceptionIfIDNotFound() {
		Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
	        .isThrownBy(() -> recipeService.delete(1L))
	        .withMessage("Recipe with ID: 1 Not Found!");
	}

}
