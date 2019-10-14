package pets.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import pets.Specie;

@CrossOrigin(origins="*")
public interface SpecieRepository 
         extends ReactiveCrudRepository<Specie, String> {

}