package tacos.messaging;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;
import tacos.Order;
import jakarta.jms.Destination;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

    private JmsTemplate jms;
    private Destination orderQueue;

    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(Order order) {
        jms.convertAndSend(orderQueue, order, this::addOrderSource); 

    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}