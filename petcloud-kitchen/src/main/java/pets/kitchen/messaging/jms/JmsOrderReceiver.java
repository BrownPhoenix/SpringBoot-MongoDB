package pets.kitchen.messaging.jms;

import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import pets.Order;
import pets.kitchen.OrderReceiver;

@Profile("jms-template")
@Component("templateOrderReceiver")
public class JmsOrderReceiver implements OrderReceiver {

  private JmsTemplate jms;

  public JmsOrderReceiver(JmsTemplate jms) {
    this.jms = jms;
  }
  
  @Override
  public Order receiveOrder() {
    return (Order) jms.receiveAndConvert("petcloud.order.queue");
  }
  
}
