package pets;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Pet {

  private String name;
  
  private Date createdAt;

  private List<Specie> species;

}
