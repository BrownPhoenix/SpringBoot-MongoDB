package pets.restclient;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import pets.Specie;

@Service
@Slf4j
public class PetCloudClient {

  private RestTemplate rest;
  private Traverson traverson;

  public PetCloudClient(RestTemplate rest, Traverson traverson) {
    this.rest = rest;
    this.traverson = traverson;
  }

  //
  // GET examples
  //
  
  /*
   * Specify parameter as varargs argument
   */
  public Specie getSpecieById(String specieId) {
    return rest.getForObject("http://localhost:8080/species/{id}", 
                             Specie.class, specieId);
  }
    
  /*
   * Specify parameters with a map
   */
  public Specie getSpecieById2(String specieId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", specieId);
    return rest.getForObject("http://localhost:8080/species/{id}",
        Specie.class, urlVariables);  
  }
    
  /*
   * Request with URI instead of String
   */
  public Specie getSpecieById3(String specieId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", specieId);
    URI url = UriComponentsBuilder
              .fromHttpUrl("http://localhost:8080/species/{id}")
              .build(urlVariables);
    return rest.getForObject(url, Specie.class);
  }
    
  /*
   * Use getForEntity() instead of getForObject()
   */
  public Specie getSpecieById4(String specieId) {
    ResponseEntity<Specie> responseEntity =
        rest.getForEntity("http://localhost:8080/species/{id}",
            Specie.class, specieId);
    log.info("Fetched time: " +
            responseEntity.getHeaders().getDate());
    return responseEntity.getBody();
  }
  
  public List<Specie> getAllSpecies() {
    return rest.exchange("http://localhost:8080/species", 
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Specie>>() {})
        .getBody();
  }
  
  //
  // PUT examples
  //
  
  public void updateSpecie(Specie specie) {
    rest.put("http://localhost:8080/species/{id}",
          specie, specie.getId());
  }
  
  //
  // POST examples
  //
  public Specie createSpecie(Specie specie) {
    return rest.postForObject("http://localhost:8080/species",
        specie, Specie.class);
  }
  
  public URI createSpecie2(Specie specie) {
    return rest.postForLocation("http://localhost:8080/species",
        specie, Specie.class);
  }
  
  public Specie createSpecie3(Specie specie) {
    ResponseEntity<Specie> responseEntity =
           rest.postForEntity("http://localhost:8080/species",
                              specie,
                              Specie.class);
    log.info("New resource created at " +
             responseEntity.getHeaders().getLocation());
    return responseEntity.getBody();
  }
  
  //
  // DELETE examples
  //
  
  public void deleteSpecie(Specie specie) {
    rest.delete("http://localhost:8080/species/{id}",
        specie.getId());
  }
  
  //
  // Traverson with RestTemplate examples
  //
  
  public Iterable<Specie> getAllSpeciesWithTraverson() {
    ParameterizedTypeReference<Resources<Specie>> specieType =
        new ParameterizedTypeReference<Resources<Specie>>() {};
    Resources<Specie> specieRes =
        traverson
          .follow("species")
          .toObject(specieType);
    return specieRes.getContent();
  }
  
  public Specie addSpecie(Specie specie) {
    String speciesUrl = traverson
        .follow("species")
        .asLink()
        .getHref();
    return rest.postForObject(speciesUrl,
                              specie,
                              Specie.class);
  }
  
}
