package com.devtiro.tickets.domain.dto.ListingTickets;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ListTicketTicketTypeResponseDto {

    private UUID id;
    private String name;
    private BigDecimal price;


}
