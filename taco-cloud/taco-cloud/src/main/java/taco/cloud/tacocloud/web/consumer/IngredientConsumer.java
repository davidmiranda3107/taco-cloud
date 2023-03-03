package taco.cloud.tacocloud.web.consumer;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import taco.cloud.tacocloud.Ingredient;

public class IngredientConsumer {
    RestTemplate rest = new RestTemplate();

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/ingredients/{id}",
             Ingredient.class, ingredientId);
    }

    public Ingredient getIngredientByIdMap(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return rest.getForObject("http://localhost:8080/ingredients/{id}",
             Ingredient.class, urlVariables);
    }

    public Ingredient getIngredientByIdUri(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:8080/ingredients/{id}")
                    .build(urlVariables);
        return rest.getForObject(url, Ingredient.class);
    }

    public Ingredient getIngredientByIdEntity(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
        rest.getForEntity("http://localhost:8080/ingredients/{id}",
        Ingredient.class, ingredientId);
        System.out.println("Fetched time: {}" + responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        rest.put("http://localhost:8080/ingredients/{id}",
        ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete("http://localhost:8080/ingredients/{id}",
        ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
    }

    public URI createIngredientUri(Ingredient ingredient) {
        return rest.postForLocation("http://localhost:8080/ingredients",
        ingredient);
    }

    public Ingredient createIngredientEntity(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
            rest.postForEntity("http://localhost:8080/ingredients",
            ingredient, Ingredient.class);
        System.out.println("New resource created at {}" + responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }

}
