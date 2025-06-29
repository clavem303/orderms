package br.com.clavem303.btgpactual.orderms.service;

import br.com.clavem303.btgpactual.orderms.controller.dto.OrderResponse;
import br.com.clavem303.btgpactual.orderms.dto.OrderCreatedEvent;
import br.com.clavem303.btgpactual.orderms.entity.OrderEntity;
import br.com.clavem303.btgpactual.orderms.entity.OrderItem;
import br.com.clavem303.btgpactual.orderms.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderCreatedEvent event) {
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getOrderItems(event));
        entity.setTotalPrice(getTotalPrice(event));
        orderRepository.save(entity);
    }

    public Page<OrderResponse> findAllByCustomerId(
            Long customerId,
            PageRequest pageRequest
            ) {
        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponse::fromEntity);
    }

    private BigDecimal getTotalPrice(OrderCreatedEvent event) {
       return event.itens()
               .stream()
               .map(i->i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
               .reduce(BigDecimal::add)
               .orElse(BigDecimal.ZERO);
    }

    private List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream().map(
                i -> new OrderItem(i.protudo(), i.quantidade(), i.preco()
                )
        ).toList();
    }
}
