package com.devtiro.tickets.domain.dto.creatingEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketTypeRequestDto {
    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
    private String description;
}
