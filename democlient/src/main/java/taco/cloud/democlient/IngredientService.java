package taco.cloud.democlient;

import org.springframework.data.repository.CrudRepository;

public interface IngredientService extends CrudRepository<Ingredient, String> {
    
}
