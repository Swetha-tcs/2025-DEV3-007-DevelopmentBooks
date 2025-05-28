package com.development.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

import com.development.softwarebooks.SoftwareBooksApplication;
import com.development.softwarebooks.config.DiscountProperties;
import com.development.softwarebooks.config.DiscountProperties.Titles;
import com.development.softwarebooks.service.PricingService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = SoftwareBooksApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PricingControllerTest {

	private final MockMvc mockMvc;
	private final ObjectMapper objectMapper;

	@MockBean
	private PricingService pricingService;

	@Autowired
	private DiscountProperties discountProperties;

	private Titles titles;

	private static final String PRICE_API_ENDPOINT = "/api/price";

	// Named constants
	private static final double PRICE_ONE_BOOK = 50.0;
	private static final double PRICE_TWO_BOOKS_DISCOUNTED = 95.0;
	private static final double PRICE_THREE_BOOKS_DISCOUNTED = 135.0;
	private static final double PRICE_FIVE_BOOKS_DISCOUNTED = 320.0;

	@Autowired
	public PricingControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}

	@BeforeAll
	void setUp() {
		this.titles = discountProperties.getTitles();
	}

	static Stream<Arguments> provideBookTitlesAndExpectedPrices() {
		PricingControllerTest testInstance = new PricingControllerTest(null, null);
		testInstance.titles = new DiscountProperties.Titles();
		testInstance.titles.setCleanCode("Clean Code");
		testInstance.titles.setCleanCoder("The Clean Coder");
		testInstance.titles.setCleanArchitecture("Clean Architecture");
		testInstance.titles.setTdd("Test Driven Development by Example");
		testInstance.titles.setLegacyCode("Working Effectively with Legacy Code");

		return Stream.of(
			Arguments.of(List.of(testInstance.titles.getCleanCode()), PRICE_ONE_BOOK),
			Arguments.of(List.of(testInstance.titles.getCleanCode(), testInstance.titles.getCleanCoder()), PRICE_TWO_BOOKS_DISCOUNTED),
			Arguments.of(List.of(testInstance.titles.getCleanCode(), testInstance.titles.getCleanCoder(), testInstance.titles.getCleanArchitecture()), PRICE_THREE_BOOKS_DISCOUNTED),
			Arguments.of(List.of(
				testInstance.titles.getCleanCoder(),
				testInstance.titles.getCleanCode(),
				testInstance.titles.getCleanArchitecture(),
				testInstance.titles.getTdd(),
				testInstance.titles.getLegacyCode()
			), PRICE_FIVE_BOOKS_DISCOUNTED)
		);
	}

	@ParameterizedTest
	@MethodSource("provideBookTitlesAndExpectedPrices")
	@DisplayName("Should return correct total price for given list of book titles")
	void calculatePrice_ReturnsCorrectPrice(List<String> selectedBookTitles, double expectedTotalPrice) throws Exception {
		when(pricingService.calculatePrice(org.mockito.ArgumentMatchers.any())).thenReturn(expectedTotalPrice);

		mockMvc.perform(post(PRICE_API_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(selectedBookTitles)))
				.andExpect(status().isOk())
				.andExpect(content().string(String.valueOf(expectedTotalPrice)));
	}
}