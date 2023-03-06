package taco.cloud.democlient;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
    
    @Id
    private String id;
    private String name;
}
