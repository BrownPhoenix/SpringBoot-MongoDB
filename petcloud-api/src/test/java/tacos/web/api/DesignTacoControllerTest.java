package tacos.web.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import pets.Specie;
import pets.Pet;
import pets.Specie.Type;
import pets.data.PetRepository;
import pets.web.api.DesignPetController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DesignTacoControllerTest {

  @Test
  public void shouldReturnRecentTacos() {
    Pet[] tacos = {
        testTaco(1L), testTaco(2L),
        testTaco(3L), testTaco(4L),
        testTaco(5L), testTaco(6L),
        testTaco(7L), testTaco(8L),
        testTaco(9L), testTaco(10L),
        testTaco(11L), testTaco(12L),
        testTaco(13L), testTaco(14L),
        testTaco(15L), testTaco(16L)};
    Flux<Pet> tacoFlux = Flux.just(tacos);
    
    PetRepository tacoRepo = Mockito.mock(PetRepository.class);
    when(tacoRepo.findAll()).thenReturn(tacoFlux);
    
    WebTestClient testClient = WebTestClient.bindToController(
        new DesignPetController(tacoRepo))
        .build();
    
    testClient.get().uri("/design/recent")
      .exchange()
      .expectStatus().isOk()
      .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$").isNotEmpty()
        .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
        .jsonPath("$[0].name").isEqualTo("Taco 1")
        .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
        .jsonPath("$[1].name").isEqualTo("Taco 2")
        .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
        .jsonPath("$[11].name").isEqualTo("Taco 12")
        .jsonPath("$[12]").doesNotExist();
  }
  
  @Test
  public void shouldSaveATaco() {
    PetRepository tacoRepo = Mockito.mock(
                PetRepository.class);
    Mono<Pet> unsavedTacoMono = Mono.just(testTaco(null));
    Pet savedTaco = testTaco(null);
    Mono<Pet> savedTacoMono = Mono.just(savedTaco);
    
    when(tacoRepo.save(any())).thenReturn(savedTacoMono);
    
    WebTestClient testClient = WebTestClient.bindToController(
        new DesignPetController(tacoRepo)).build();
    
    testClient.post()
        .uri("/design")
        .contentType(MediaType.APPLICATION_JSON)
        .body(unsavedTacoMono, Pet.class)
      .exchange()
      .expectStatus().isCreated()
      .expectBody(Pet.class)
        .isEqualTo(savedTaco);
  }

  
  private Pet testTaco(Long number) {
    Pet pet = new Pet();
    pet.setId(number != null ? number.toString(): "TESTID");
    pet.setName("Taco " + number);
    List<Specie> species = new ArrayList<>();
    species.add(
        new Specie("INGA", "Ingredient A", Type.ANIMAL));
    species.add(
        new Specie("INGB", "Ingredient B", Type.GENDER));
    pet.setSpecies(species);
    return pet;
  }
}
