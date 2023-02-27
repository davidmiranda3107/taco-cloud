package taco.cloud.tacocloud.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import taco.cloud.tacocloud.Taco;
import taco.cloud.tacocloud.Ingredient;
import taco.cloud.tacocloud.Order;
import taco.cloud.tacocloud.User;
import taco.cloud.tacocloud.Ingredient.Type;
import taco.cloud.tacocloud.data.IngredientRepository;
import taco.cloud.tacocloud.data.TacoRepository;
import taco.cloud.tacocloud.data.UserRepository;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private TacoRepository designRepo;

    private UserRepository userRepo;

    public DesignTacoController(IngredientRepository ingredientRepo,
            TacoRepository designRepo,
            UserRepository userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
        this.userRepo = userRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
    
    @GetMapping
    public String showDesignForm(Model model, Principal principal) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
            filterByType(ingredients, type));
        }

        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        model.addAttribute("user", user);
 
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, 
            Errors errors,
            @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        
        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
