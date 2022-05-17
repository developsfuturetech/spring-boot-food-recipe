package com.capgemini.recipes.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.recipes.dto.RecipeDto;
import com.capgemini.recipes.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/recipes")
@Slf4j
public class RecipeController {

	@Autowired
	private  RecipeService recipeService;


	/** Returns all recipes information  */
	@GetMapping("/allrecipes")
	public ResponseEntity<List<RecipeDto>> getAllRecipes(){
		log.debug(":::::::In getAllRecipes method :::::::::::");
		return status(HttpStatus.OK).body(recipeService.getAllRecipes());
	}

	/**
	 * Returns recipe data based on the id
	 * 
	 * @param id - Recipe ID
	 */
	@GetMapping("/show/{id}")
	public ResponseEntity<RecipeDto>  getRecipeById(@PathVariable Long id){ 
		log.debug(":::::::In getAllRecipes method :::::::::::");
		return status(HttpStatus.OK).body(recipeService.findById(id)); 
	}

	/**
	 * Returns recipe data based on the id
	 * 
	 * @param recipeDto - RecipeDto
	 * @param id - Recipe ID
	 */
	@PostMapping
	public ResponseEntity<RecipeDto> createPost(@Valid @RequestBody RecipeDto recipeDto) {
		log.debug(":::::::In createpost method :::::::::::");
		recipeService.save(recipeDto);
		return new	ResponseEntity<>(HttpStatus.CREATED); 
	}

	/**
	 * Update recipe data based on the id
	 * 
	 * @param recipeDto - RecipeDto
	 * @param id - Recipe ID	
	 */
	@PutMapping("/{id}")
	public ResponseEntity<RecipeDto> update(@Valid @RequestBody RecipeDto recipeDto, @PathVariable Long id) {
		log.debug(":::::::In update method :::::::::::");
		recipeService.update(recipeDto, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * deletes recipe data based on the id
	 * 
	 * @param id - Recipe ID
	 */
	public void delete(@PathVariable Long id) {
		log.debug(":::::::In delete method :::::::::::");
		recipeService.delete(id);
	}

}
