package pets.restclient;

import java.net.URI;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import pets.Specie;

@SpringBootConfiguration
@ComponentScan
@Slf4j
public class RestExamples {

  public static void main(String[] args) {
    SpringApplication.run(RestExamples.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
  @Bean
  public CommandLineRunner fetchSpecies(PetCloudClient petCloudClient) {
    return args -> {
      log.info("----------------------- GET -------------------------");
      log.info("GETTING INGREDIENT BY IDE");
      log.info("Specie:  " + petCloudClient.getSpecieById("CHED"));
      log.info("GETTING ALL INGREDIENTS");
      List<Specie> species = petCloudClient.getAllSpecies();
      log.info("All species:");
      for (Specie specie : species) {
        log.info("   - " + specie);
      }
    };
  }
  
  @Bean
  public CommandLineRunner putAnSpecie(PetCloudClient petCloudClient) {
    return args -> {
      log.info("----------------------- PUT -------------------------");
      Specie before = petCloudClient.getSpecieById("LETC");
      log.info("BEFORE:  " + before);
      petCloudClient.updateSpecie(new Specie("LETC", "Shredded Lettuce", Specie.Type.COLOUR));
      Specie after = petCloudClient.getSpecieById("LETC");
      log.info("AFTER:  " + after);
    };
  }
  
  @Bean
  public CommandLineRunner addAnSpecie(PetCloudClient petCloudClient) {
    return args -> {
      log.info("----------------------- POST -------------------------");
      Specie chix = new Specie("CHIX", "Shredded Chicken", Specie.Type.GENDER);
      Specie chixAfter = petCloudClient.createSpecie(chix);
      log.info("AFTER=1:  " + chixAfter);
      Specie beefFajita = new Specie("BFFJ", "Beef Fajita", Specie.Type.GENDER);
      URI uri = petCloudClient.createSpecie2(beefFajita);
      log.info("AFTER-2:  " + uri);      
      Specie shrimp = new Specie("SHMP", "Shrimp", Specie.Type.GENDER);
      Specie shrimpAfter = petCloudClient.createSpecie3(shrimp);
      log.info("AFTER-3:  " + shrimpAfter);      
    };
  }

  
  @Bean
  public CommandLineRunner deleteAnSpecie(PetCloudClient petCloudClient) {
    return args -> {
      log.info("----------------------- DELETE -------------------------");
      Specie before = petCloudClient.getSpecieById("CHIX");
      log.info("BEFORE:  " + before);
      petCloudClient.deleteSpecie(before);
      Specie after = petCloudClient.getSpecieById("CHIX");
      log.info("AFTER:  " + after);
      before = petCloudClient.getSpecieById("BFFJ");
      log.info("BEFORE:  " + before);
      petCloudClient.deleteSpecie(before);
      after = petCloudClient.getSpecieById("BFFJ");
      log.info("AFTER:  " + after);
      before = petCloudClient.getSpecieById("SHMP");
      log.info("BEFORE:  " + before);
      petCloudClient.deleteSpecie(before);
      after = petCloudClient.getSpecieById("SHMP");
      log.info("AFTER:  " + after);
    };
  }
  
  //
  // Traverson examples
  //
  
  @Bean
  public Traverson traverson() {
    Traverson traverson = new Traverson(
        URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
    return traverson;
  }
  
  @Bean
  public CommandLineRunner traversonGetSpecies(PetCloudClient petCloudClient) {
    return args -> {
      Iterable<Specie> species = petCloudClient.getAllSpeciesWithTraverson();
      log.info("----------------------- GET INGREDIENTS WITH TRAVERSON -------------------------");
      for (Specie specie : species) {
        log.info("   -  " + specie);
      }
    };
  }
  
  @Bean
  public CommandLineRunner traversonSaveSpecie(PetCloudClient petCloudClient) {
    return args -> {
      Specie pico = petCloudClient.addSpecie(
          new Specie("PICO", "Pico de Gallo", Specie.Type.PERSONALITY));
      List<Specie> allSpecies = petCloudClient.getAllSpecies();
      log.info("----------------------- ALL INGREDIENTS AFTER SAVING PICO -------------------------");
      for (Specie specie : allSpecies) {
        log.info("   -  " + specie);
      }
      petCloudClient.deleteSpecie(pico);
    };
  }

}
