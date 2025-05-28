package com.development.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.development.softwarebooks.SoftwareBooksApplication;
import com.development.softwarebooks.controller.PricingController;
import com.development.softwarebooks.service.PricingService;

@WebMvcTest(controllers = PricingController.class)
@ContextConfiguration(classes = SoftwareBooksApplication.class)

public class PricingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PricingService pricingService;

	@Test
	void calculatePrice_ReturnsExpectedPrice() throws Exception {
		// Given
		List<String> bookTitles = List.of("Clean Code", "The Clean Coder", "Clean Architecture");
		double expectedPrice = 23.5;

		// Mock the service method to return expectedPrice regardless of input
		when(pricingService.calculatePrice(org.mockito.ArgumentMatchers.any())).thenReturn(expectedPrice);

		// Convert the bookTitles list to JSON array string
		String jsonRequest = "[\"Clean Code\",\"The Clean Coder\",\"Clean Architecture\"]";

		// When & Then
		mockMvc.perform(post("/api/price").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andExpect(status().isOk()).andExpect(content().string(String.valueOf(expectedPrice)));
	}
}