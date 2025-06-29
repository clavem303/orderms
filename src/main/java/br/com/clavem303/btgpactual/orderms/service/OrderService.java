package br.com.clavem303.btgpactual.orderms.service;

import br.com.clavem303.btgpactual.orderms.controller.dto.OrderResponse;
import br.com.clavem303.btgpactual.orderms.dto.OrderCreatedEvent;
import br.com.clavem303.btgpactual.orderms.entity.OrderEntity;
import br.com.clavem303.btgpactual.orderms.entity.OrderItem;
import br.com.clavem303.btgpactual.orderms.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(
            OrderRepository orderRepository,
            MongoTemplate mongoTemplate
    ) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
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

    public BigDecimal findTotalPriceOnOrdersByCustomerId(Long customerId) {
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("totalPrice").as("totalPrice")
        );
        var response = mongoTemplate.aggregate(aggregations, "td_orders", Document.class);
        return new BigDecimal(Objects.requireNonNull(response.getUniqueMappedResult()).get("totalPrice").toString());
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
