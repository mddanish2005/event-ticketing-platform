package com.devtiro.tickets.domain.dto.listingEvents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListEventTicketTypeResponseDto {
    private String name;
         private BigDecimal price;
         private Integer totalAvailable;
         private String description;
}
