package com.devtiro.tickets.domain.dto.updatingEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketTypeRequestDto {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
    private String description;
}
