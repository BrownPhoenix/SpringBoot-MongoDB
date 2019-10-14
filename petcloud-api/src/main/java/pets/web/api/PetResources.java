package pets.web.api;

import java.util.List;

import org.springframework.hateoas.Resources;

public class PetResources extends Resources<PetResource> {
  public PetResources(List<PetResource> petResources) {
    super(petResources);
  }
}