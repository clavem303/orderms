package br.com.clavem303.btgpactual.orderms.listener;

import br.com.clavem303.btgpactual.orderms.dto.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static br.com.clavem303.btgpactual.orderms.congif.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message){
        logger.info("Received order created event: {}", message.getPayload());
    }

    @Bean
    public Declarable orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE);
    }
}
