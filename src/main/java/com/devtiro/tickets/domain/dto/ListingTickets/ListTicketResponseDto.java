package com.devtiro.tickets.domain.dto.ListingTickets;


import com.devtiro.tickets.domain.enums.TicketStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDto {

    private UUID id;
    private TicketStatusEnum status;
    @JsonProperty("ticketType")
    private ListTicketTicketTypeResponseDto listTicketTicketTypeResponseDto;



}
