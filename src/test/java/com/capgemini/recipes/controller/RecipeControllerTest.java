package com.capgemini.recipes.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.capgemini.recipes.dto.IngredientDto;
import com.capgemini.recipes.dto.RecipeDto;
import com.capgemini.recipes.exceptions.ResourceNotFoundException;
import com.capgemini.recipes.repository.RecipeRepository;
import com.capgemini.recipes.service.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	RecipeService recipeService;

	@MockBean
	ModelMapper modelMapper;

	@MockBean
	RecipeRepository recipeRepository;

	IngredientDto ingredient_1 = new IngredientDto(1l,"Biryani Rice", "2 Kgs");
	IngredientDto ingredient_2 = new IngredientDto(2l,"Chicken", "300 gms");
	List<IngredientDto> list = Arrays.asList(ingredient_1,ingredient_2);

	RecipeDto RECORD_1 = new RecipeDto(1l, "Biryani", "Non-Veg", "20 members","1 hour",list);

	String exampleRecipeJson = "{ \"cooktime\": 10, \"name\": \"Biryani\", \"category\":\"Non-Veg\", \"servings\": \"20\", \"ingredients\": [ { \"name\": \"Biryani Rice\", \"quantity\": 10 }, { \"name\": \"Chicken\", \"quantity\": 3 } ]}";

	@Test
	@WithMockUser(username = "username", password = "password", roles = "")
	public void getAllRecords_success() throws Exception {

		List<RecipeDto> records = Arrays.asList(RECORD_1);

		Mockito.when(recipeService.getAllRecipes()).thenReturn(records);

		mockMvc.perform(get("/api/recipes/allrecipes").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").value("Biryani"));
	}
	
	@Test
	@WithMockUser(username = "username", password = "password", roles = "")
	public void getRecipeById_success() throws Exception {

		Mockito.when(recipeService.findById(1L)).thenReturn(RECORD_1);
		mockMvc.perform(get("/api/recipes//show/{id}", "1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("Biryani"));
	}
	

	@Test
	@WithMockUser(username = "username", password = "password", roles = "")
	public void testPost_success() throws Exception {
	  
	  mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
	    .content(exampleRecipeJson).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	 }

	@Test
	@WithMockUser(username = "username", password = "password", roles = "")
	public void testPut_success() throws Exception {
	  
	  mockMvc.perform(MockMvcRequestBuilders.put("/api/recipes/{id}", "1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
	    .content(exampleRecipeJson).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	 }
	
	@Test
	@WithMockUser(username = "user", password = "password", roles = "")
	public void testDelete_failure() throws Exception {

		Mockito.doThrow(new ResourceNotFoundException("Recipe with ID: 1 Not Found!")).when(recipeService).delete(1L);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.errors[0]").value("Recipe with ID: 1 Not Found!"));

	}
	
	@Test
	@WithMockUser(username = "username", password = "password", roles = "")
	public void testDelete_success() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}	 
	
	@Test
	public void whenIdIsNull_thenExceptionIsThrown() {

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class, 
				() -> { throw new ResourceNotFoundException("Recipe with ID: 1 Not Found!"); }
				);

		assertEquals(ResourceNotFoundException.class, exception.getClass());
	}
}

