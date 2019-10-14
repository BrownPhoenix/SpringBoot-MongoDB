package pets.web.api;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import pets.Specie;
import pets.Specie.Type;

public class SpecieResource extends ResourceSupport {

  @Getter
  private String name;

  @Getter
  private Type type;
  
  public SpecieResource(Specie specie) {
    this.name = specie.getName();
    this.type = specie.getType();
  }

}
