package br.com.clavem303.btgpactual.orderms.controller.dto;

import org.springframework.data.domain.Page;

public record PaginationResponse(
        Integer page,
        Integer pageSize,
        Long totalElements,
        int totalPages
) {
    public static PaginationResponse fromPage(Page<?> page) {
        return new PaginationResponse(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
