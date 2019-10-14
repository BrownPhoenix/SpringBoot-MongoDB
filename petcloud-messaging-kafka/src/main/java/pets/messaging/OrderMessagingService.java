package pets.messaging;

import pets.Order;

public interface OrderMessagingService {

  void sendOrder(Order order);
  
}
