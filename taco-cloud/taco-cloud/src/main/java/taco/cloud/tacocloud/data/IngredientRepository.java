package taco.cloud.tacocloud.data;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.tacocloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    
    // Iterable<Ingredient> findAll();

    // Ingredient findOne(String id);

    // Ingredient save(Ingredient ingredient);
    
}