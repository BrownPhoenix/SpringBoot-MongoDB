package pets.web.api;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import pets.Pet;

@Relation(value="pet", collectionRelation="pets")
public class PetResource extends ResourceSupport {

  private static final SpecieResourceAssembler 
            petAssembler = new SpecieResourceAssembler();
  
  @Getter
  private final String name;

  @Getter
  private final Date createdAt;

  @Getter
  private final List<SpecieResource> pets;
  
  public PetResource(Pet pet) {
    this.name = pet.getName();
    this.createdAt = pet.getCreatedAt();
    this.pets = 
        petAssembler.toResources(pet.getSpecies());
  }
  
}
