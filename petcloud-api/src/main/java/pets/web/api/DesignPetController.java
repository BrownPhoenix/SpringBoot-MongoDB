package pets.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//end::recents[]
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//tag::recents[]
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pets.Pet;
import pets.data.PetRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignPetController {
  private PetRepository petRepo;

  public DesignPetController(PetRepository petRepo) {
    this.petRepo = petRepo;
  }

  @GetMapping("/recent")
  public Flux<Pet> recentPets() {
    return petRepo.findAll().take(12);
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Pet> postPet(@RequestBody Pet pet) {
    return petRepo.save(pet);
  }

  @GetMapping("/{id}")
  public Mono<Pet> petById(@PathVariable("id") String id) {
    return petRepo.findById(id);
  }

}
