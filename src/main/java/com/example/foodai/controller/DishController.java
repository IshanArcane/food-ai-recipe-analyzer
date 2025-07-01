package com.example.foodai.controller;

import com.example.foodai.dto.RecipeResponse;
import com.example.foodai.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping("/analyze")
    public ResponseEntity<RecipeResponse> analyzeDish(@RequestParam("file") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            RecipeResponse response = dishService.analyzeDish(imageBytes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            RecipeResponse errorResponse = new RecipeResponse();
            errorResponse.setDishName("Error analyzing image");
            errorResponse.setInstructions(List.of(e.getMessage()));
            errorResponse.setIngredients(List.of());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
