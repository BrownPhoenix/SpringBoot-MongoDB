package pets.web.api;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pets.Specie;

class SpecieResourceAssembler extends 
          ResourceAssemblerSupport<Specie, SpecieResource> {

  public SpecieResourceAssembler() {
    super(SpecieController.class, SpecieResource.class);
  }

  @Override
  public SpecieResource toResource(Specie specie) {
    return createResourceWithId(specie.getId(), specie);
  }
  
  @Override
  protected SpecieResource instantiateResource(
      Specie specie) {
    return new SpecieResource(specie);
  }

}