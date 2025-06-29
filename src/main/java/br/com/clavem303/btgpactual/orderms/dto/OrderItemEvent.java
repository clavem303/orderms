package br.com.clavem303.btgpactual.orderms.dto;

import java.math.BigDecimal;

public record OrderItemEvent(String protudo,
                             Integer quantidade,
                             BigDecimal preco) {

}
