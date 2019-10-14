package pets.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pets.Order;
import pets.kitchen.OrderReceiver;

@Profile("rabbitmq-template")
@Component("templateOrderReceiver")
public class RabbitOrderReceiver implements OrderReceiver {

  private RabbitTemplate rabbit;

  public RabbitOrderReceiver(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
  
  public Order receiveOrder() {
    return (Order) rabbit.receiveAndConvert("petcloud.order.queue");
  }
  
}
