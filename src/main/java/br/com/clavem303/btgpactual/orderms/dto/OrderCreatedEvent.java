package br.com.clavem303.btgpactual.orderms.dto;

import java.util.List;

public record OrderCreatedEvent(Long codigoPedido,
                                Long codigCliente,
                                List<OrderItemEvent> itens) {

}
