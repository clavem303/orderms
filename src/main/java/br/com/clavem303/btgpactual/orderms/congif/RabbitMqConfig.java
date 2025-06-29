package br.com.clavem303.btgpactual.orderms.congif;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String ORDER_CREATED_QUEUE = "btg-pactual-order-created";
}
