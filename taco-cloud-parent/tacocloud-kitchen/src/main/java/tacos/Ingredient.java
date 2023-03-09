package tacos;

import java.io.Serializable;

import lombok.Data;

@Data
public class Ingredient implements Serializable {

  private final String name;
  private final Type type;
  
  public static enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }
  
}