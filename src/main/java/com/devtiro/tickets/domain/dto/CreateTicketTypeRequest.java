package com.devtiro.tickets.domain.dto;

import com.devtiro.tickets.domain.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketTypeRequest {

    private String name;
    private BigDecimal price;
    private Integer totalAvailable;

}
