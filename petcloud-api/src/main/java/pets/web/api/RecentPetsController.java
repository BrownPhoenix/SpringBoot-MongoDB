package pets.web.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import pets.data.PetRepository;
import reactor.core.publisher.Mono;

@RepositoryRestController
public class RecentPetsController {

  private PetRepository petRepo;

  public RecentPetsController(PetRepository petRepo) {
    this.petRepo = petRepo;
  }

  @GetMapping(path="/pets/recent", produces="application/hal+json")
  public Mono<ResponseEntity<Resources<PetResource>>> recentPets() {
    return petRepo.findAll()
        .take(12)
        .collectList()
        .map(pets -> {
          List<PetResource> petResources = 
              new PetResourceAssembler().toResources(pets);
          Resources<PetResource> recentResources = 
                  new Resources<PetResource>(petResources);
          
          recentResources.add(
              linkTo(methodOn(RecentPetsController.class).recentPets())
                  .withRel("recents"));
          return new ResponseEntity<>(recentResources, HttpStatus.OK);
        });
  }

}
