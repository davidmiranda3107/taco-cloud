package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

@Component
public class RabbitOrderReceiver implements OrderReceiver {

    private RabbitTemplate rabbit;

    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public Order receiveOrder() {
        return (Order) rabbit.receiveAndConvert("tacocloud.order.queue");
    }
    
}
