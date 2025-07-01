# 🍽️ Food AI Recipe Analyzer

This is a **Spring Boot application** that allows you to analyze a food dish from an image and fetch a complete recipe (ingredients and instructions) dynamically from the internet.  

The application integrates two powerful APIs:
- **Clarifai API** for food dish recognition.
- **Spoonacular API** for retrieving detailed recipes.

---

## 🚀 Features

✅ Upload a dish image (e.g., French fries, pasta, biryani).  
✅ Automatically detect the dish name using AI (Clarifai).  
✅ Fetch dynamic recipe details, including:
- List of ingredients
- Step-by-step cooking instructions

✅ Get a clean JSON response, ready to integrate into any frontend or app.

---

## 🏗️ Tech Stack

- **Java 17**
- **Spring Boot 3.4.5**
- **Maven**
- **Clarifai API** (for image recognition)
- **Spoonacular API** (for recipes)
- **Postman** (for testing)

---

## 💡 How it Works

1️⃣ Upload a food image via `/api/dishes/analyze` endpoint.  
2️⃣ Application sends the image to **Clarifai API**, which returns the predicted dish name.  
3️⃣ Using the dish name, it queries **Spoonacular API** to fetch detailed recipe data.  
4️⃣ Returns a JSON response with dish name, ingredients, and instructions.

