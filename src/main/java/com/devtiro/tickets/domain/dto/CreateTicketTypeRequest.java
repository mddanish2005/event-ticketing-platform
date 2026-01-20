package com.devtiro.tickets.domain.dto;

import com.devtiro.tickets.domain.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTicketTypeRequest {

    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
    private String description;

}
