package com.capgemini.recipes.exceptions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.capgemini.recipes.service.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	RecipeService recipeService;

	String exampleRecipeJson = "{ \"cooktime\": 10, \"name\": \"Biryani\", \"category\":\"Non-Veg\", \"ingredients\": [ { \"name\": \"Biryani Rice\", \"quantity\": 10 }, { \"name\": \"Chicken\", \"quantity\": 3 } ]}";
	String recipeJson = " \"cooktime\": 10, \"name\": \"Biryani\", \"category\":\"Non-Veg\", \"ingredients\": [ { \"name\": \"Biryani Rice\", \"quantity\": 10 }, { \"name\": \"Chicken\", \"quantity\": 3 } ]}";

	
	@Test
	public void testUnAuthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
			 .content(recipeJson).accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(username = "user", password = "password", roles = "")
	public void testResourceNotFound() throws Exception {
		Mockito.doThrow(new ResourceNotFoundException("Recipe with ID: 1 Not Found!")).when(recipeService).delete(1L);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.errors[0]").value("Recipe with ID: 1 Not Found!"));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "")
	public void testDeleteMethodArgumentTypeMismatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/{id}", "asdf")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.message").value("Mismatch Type"));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "")
	public void testMethodArgumentNotValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
			 .content(exampleRecipeJson).accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").value("Validation Errors"));
	}

	
}	