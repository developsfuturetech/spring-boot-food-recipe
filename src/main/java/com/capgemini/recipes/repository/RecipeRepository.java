package com.capgemini.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.recipes.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
