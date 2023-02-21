package taco.cloud.tacocloud.data;

import taco.cloud.tacocloud.Ingredient;

public interface IngredientRepository {
    
    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
    
}