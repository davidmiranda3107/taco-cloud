package taco.cloud.tacocloud;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.cloud.tacocloud.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    
    private IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
        return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
    }
}
