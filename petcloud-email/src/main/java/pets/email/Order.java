package pets.email;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Order {
  
  private final String email;
  private List<Pet> pets = new ArrayList<>();

  public void addPet(Pet pet) {
    this.pets.add(pet);
  }
  
}
