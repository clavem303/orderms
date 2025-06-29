package br.com.clavem303.btgpactual.orderms.controller.dto;

import br.com.clavem303.btgpactual.orderms.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(
        Long orderId,
        Long costumerId,
        BigDecimal totalPrice
) {

    public static OrderResponse fromEntity(OrderEntity entity) {
        return new OrderResponse(
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getTotalPrice()
        );
    }
}