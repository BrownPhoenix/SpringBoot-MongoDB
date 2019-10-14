package pets.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import pets.Order;
import pets.User;
import reactor.core.publisher.Flux;

public interface OrderRepository 
         extends ReactiveCrudRepository<Order, String> {

  Flux<Order> findByUserOrderByPlacedAtDesc(
          User user, Pageable pageable);

}
