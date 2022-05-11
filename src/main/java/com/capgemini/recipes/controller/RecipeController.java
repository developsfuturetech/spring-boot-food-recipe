package com.capgemini.recipes.controller;

import com.capgemini.recipes.dto.RecipeDto;
import com.capgemini.recipes.repository.RecipeRepository;
import com.capgemini.recipes.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/recipes")
@AllArgsConstructor
@Slf4j
public class RecipeController {

	@Autowired
	private  RecipeService recipeService;

	@GetMapping("/allrecipes")
	public ResponseEntity<List<RecipeDto>> getAllRecipes(){
		return status(HttpStatus.OK).body(recipeService.getAllRecipes());
	}

	@GetMapping("/show/{id}")
	public ResponseEntity<RecipeDto>  getRecipeById(@PathVariable Long id){ 
		return status(HttpStatus.OK).body(recipeService.findById(id)); 
	}

	@PostMapping
	public ResponseEntity<RecipeDto> createPost(@RequestBody RecipeDto recipeDto) {
		recipeService.save(recipeDto);
		return new	ResponseEntity<>(HttpStatus.CREATED); 
	}

	@PutMapping("/{id}")
	public ResponseEntity<RecipeDto> update(@RequestBody RecipeDto recipeDto, @PathVariable Long id) {
		try {
			RecipeDto existRecipeDto = recipeService.findById(id);

			recipeService.save(recipeDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}      
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		recipeService.delete(id);
	}

}
