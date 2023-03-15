package tacos.messaging;

import org.ietf.jgss.MessageProp;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService {
    
    private RabbitTemplate rabbit;

    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public void sendOrder(Order order) {
        // MessageConverter converter = rabbit.getMessageConverter();
        // MessageProperties props = new MessageProperties();
        // Message message = converter.toMessage(order, props);
        // rabbit.send("tacocloud.order", message);
       // rabbit.convertAndSend("tacocloud.order", order);
       rabbit.convertAndSend("tacocloud.order.queue", order, 
            new MessagePostProcessor() {

                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties props = message.getMessageProperties();
                    props.setHeader("X_ORDER_SOURCE", "WEB");
                    return message;
                }
                
            });
    }

}
