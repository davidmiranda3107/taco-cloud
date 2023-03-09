package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order implements Serializable {

  private Date placedAt;
  private String name;
  private String street;
  private String city;
  private String state;
  private String zip;

  private List<Taco> tacos = new ArrayList<>();

}