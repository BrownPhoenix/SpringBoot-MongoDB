package pets.web.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pets.Specie;
import pets.data.SpecieRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/species", produces="application/json")
@CrossOrigin(origins="*")
public class SpecieController {

  private SpecieRepository repo;

  @Autowired
  public SpecieController(SpecieRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public Flux<Specie> allSpecies() {
    return repo.findAll();
  }
  
  @GetMapping("/{id}")
  public Mono<Specie> byId(@PathVariable String id) {
    return repo.findById(id);
  }
  
  @PutMapping("/{id}")
  public void updateSpecie(@PathVariable String id, @RequestBody Specie specie) {
    if (!specie.getId().equals(id)) {
      throw new IllegalStateException("Given specie's ID doesn't match the ID in the path.");
    }
    repo.save(specie);
  }
  
  @PostMapping
  public Mono<ResponseEntity<Specie>> postSpecie(@RequestBody Mono<Specie> specie) {
    return specie
        .flatMap(repo::save)
        .map(i -> {
          HttpHeaders headers = new HttpHeaders();
          headers.setLocation(URI.create("http://localhost:8080/species/" + i.getId()));
          return new ResponseEntity<Specie>(i, headers, HttpStatus.CREATED);          
        });
  }
  
  @DeleteMapping("/{id}")
  public void deleteSpecie(@PathVariable String id) {
    repo.deleteById(id);
  }
  
}
