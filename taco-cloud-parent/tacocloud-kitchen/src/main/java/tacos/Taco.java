package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Taco implements Serializable {

  private String name;
  
  private Date createdAt;

  private List<Ingredient> ingredients = new ArrayList<>();

}