package pets.web.api;

import java.util.List;

import lombok.Data;

@Data
public class EmailOrder {

  private String email;
  private List<EmailPet> pets;
  
  @Data
  public static class EmailPet {
    private String name;
    private List<String> species;
  }
  
}
