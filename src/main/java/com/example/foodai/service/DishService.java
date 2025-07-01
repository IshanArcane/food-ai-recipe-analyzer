package com.example.foodai.service;

import com.example.foodai.config.ClarifaiConfig;
import com.example.foodai.dto.RecipeResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DishService {

    @Autowired
    private ClarifaiConfig clarifaiConfig;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SPOONACULAR_API_URL = "https://api.spoonacular.com/recipes/complexSearch";
    private static final String SPOONACULAR_API_KEY = "d483fabefe0a43e58915a8d697c0ab47";

    public RecipeResponse analyzeDish(byte[] imageBytes) {
        try {
            // Clarifai request
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            Map<String, Object> imageData = Map.of("base64", base64Image);
            Map<String, Object> data = Map.of("image", imageData);
            Map<String, Object> input = Map.of("data", data);

            Map<String, Object> userAppId = Map.of(
                    "user_id", clarifaiConfig.getUserId(),
                    "app_id", clarifaiConfig.getAppId()
            );

            Map<String, Object> requestBody = Map.of(
                    "user_app_id", userAppId,
                    "inputs", List.of(input)
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(clarifaiConfig.getApiKey());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    clarifaiConfig.getApiUrl(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to analyze image with Clarifai");
            }

            // Parse dish name
            JsonNode root = objectMapper.readTree(response.getBody());
            String dishName = root.path("outputs").get(0).path("data").path("concepts").get(0).path("name").asText();

            // Call Spoonacular to get recipe ID
            String searchUrl = SPOONACULAR_API_URL + "?query=" + dishName + "&number=1&apiKey=" + SPOONACULAR_API_KEY;
            ResponseEntity<String> searchResponse = restTemplate.getForEntity(searchUrl, String.class);

            if (!searchResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to search recipe on Spoonacular");
            }

            JsonNode searchRoot = objectMapper.readTree(searchResponse.getBody());
            JsonNode results = searchRoot.path("results");

            if (!results.isArray() || results.size() == 0) {
                return new RecipeResponse(dishName, Collections.emptyList(), Collections.emptyList());
            }

            int recipeId = results.get(0).path("id").asInt();

            // Fetch detailed recipe info
            String infoUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?includeNutrition=false&apiKey=" + SPOONACULAR_API_KEY;
            ResponseEntity<String> infoResponse = restTemplate.getForEntity(infoUrl, String.class);

            List<String> ingredients = new ArrayList<>();
            List<String> instructions = new ArrayList<>();

            if (infoResponse.getStatusCode().is2xxSuccessful()) {
                JsonNode infoRoot = objectMapper.readTree(infoResponse.getBody());

                JsonNode extendedIngredients = infoRoot.path("extendedIngredients");
                for (JsonNode ing : extendedIngredients) {
                    ingredients.add(ing.path("original").asText());
                }

                JsonNode analyzedInstructions = infoRoot.path("analyzedInstructions");
                if (analyzedInstructions.isArray() && analyzedInstructions.size() > 0) {
                    JsonNode steps = analyzedInstructions.get(0).path("steps");
                    for (JsonNode step : steps) {
                        instructions.add(step.path("step").asText());
                    }
                }
            }

            RecipeResponse recipeResponse = new RecipeResponse();
            recipeResponse.setDishName(dishName);
            recipeResponse.setIngredients(ingredients);
            recipeResponse.setInstructions(instructions);

            return recipeResponse;

        } catch (Exception e) {
            throw new RuntimeException("Error analyzing image or fetching recipe: " + e.getMessage(), e);
        }
    }
}
