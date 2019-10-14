package pets.web.api;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pets.Pet;

public class PetResourceAssembler
       extends ResourceAssemblerSupport<Pet, PetResource> {

  public PetResourceAssembler() {
    super(DesignPetController.class, PetResource.class);
  }
  
  @Override
  protected PetResource instantiateResource(Pet pet) {
    return new PetResource(pet);
  }

  @Override
  public PetResource toResource(Pet pet) {
    return createResourceWithId(pet.getId(), pet);
  }

}
