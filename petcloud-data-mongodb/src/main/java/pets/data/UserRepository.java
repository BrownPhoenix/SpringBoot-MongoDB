package pets.data;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import pets.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

  Mono<User> findByUsername(String username);
  
  Mono<User> findByEmail(String email);
  
}
