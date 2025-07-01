package com.example.foodai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClarifaiConfig {

    @Value("${clarifai.api.key}")
    private String apiKey;

    @Value("${clarifai.api.url}")
    private String apiUrl;

    @Value("${clarifai.user.id}")
    private String userId;

    @Value("${clarifai.app.id}")
    private String appId;

    @Value("${spoonacular.api.key}")
    private String spoonacularApiKey;

    @Value("${spoonacular.api.url}")
    private String spoonacularApiUrl;

    @Value("${spoonacular.api.detail.url}")
    private String spoonacularApiDetailUrl;

// Add getters for these too!


    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getAppId() {
        return appId;
    }


    public String getSpoonacularApiKey() {
        return spoonacularApiKey;
    }

    public void setSpoonacularApiKey(String spoonacularApiKey) {
        this.spoonacularApiKey = spoonacularApiKey;
    }

    public String getSpoonacularApiUrl() {
        return spoonacularApiUrl;
    }

    public void setSpoonacularApiUrl(String spoonacularApiUrl) {
        this.spoonacularApiUrl = spoonacularApiUrl;
    }

    public String getSpoonacularApiDetailUrl() {
        return spoonacularApiDetailUrl;
    }

    public void setSpoonacularApiDetailUrl(String spoonacularApiDetailUrl) {
        this.spoonacularApiDetailUrl = spoonacularApiDetailUrl;
    }
}
