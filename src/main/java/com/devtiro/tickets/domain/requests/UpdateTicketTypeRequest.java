package com.devtiro.tickets.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketTypeRequest {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
    private String description;
}
