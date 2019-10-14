package pets.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import pets.Pet;


public interface PetRepository 
         extends ReactiveCrudRepository<Pet, String> {

}
