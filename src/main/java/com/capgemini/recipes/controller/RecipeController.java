package com.capgemini.recipes.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@GetMapping("/allrecipes")
	public ResponseEntity<List<RecipeDto>> getAllRecipes(){
	    log.debug(":::::::In getAllRecipes method :::::::::::");
		return status(HttpStatus.OK).body(recipeService.getAllRecipes());
	}

	@GetMapping("/show/{id}")
	public ResponseEntity<RecipeDto>  getRecipeById(@PathVariable Long id){ 
	    log.debug(":::::::In getRecipeById method :::::::::::");
		return status(HttpStatus.OK).body(recipeService.findById(id)); 
	}

	@PostMapping
	public ResponseEntity<RecipeDto> createPost(@Valid @RequestBody RecipeDto recipeDto) {
	    log.debug(":::::::In createpost method :::::::::::");
		recipeService.save(recipeDto);
		return new	ResponseEntity<>(HttpStatus.CREATED); 
	}

	@PutMapping("/{id}")
	public ResponseEntity<RecipeDto> update(@Valid @RequestBody RecipeDto recipeDto, @PathVariable Long id) {
	    log.debug(":::::::In update method :::::::::::");
		try {
			RecipeDto existRecipeDto = recipeService.findById(id);
			recipeDto.setId(existRecipeDto.getId());
			recipeService.save(recipeDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}      
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	    log.debug(":::::::In delete method :::::::::::");
		recipeService.delete(id);
	}

}
