package com.devtiro.tickets.mappers;

import com.devtiro.tickets.domain.dto.ListingTickets.ListTicketResponseDto;
import com.devtiro.tickets.domain.dto.ListingTickets.ListTicketTicketTypeResponseDto;
import com.devtiro.tickets.domain.dto.gettingTicket.GetTicketResponseDto;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.domain.entity.Ticket;
import com.devtiro.tickets.domain.entity.TicketType;

public class TicketMapper {

    public static ListTicketResponseDto tolistTicketResponseDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        return ListTicketResponseDto.builder()
                .id(ticket.getId())
                .status(ticket.getStatus())
                .listTicketTicketTypeResponseDto(
                        tolistTicketTicketTypeResponseDto(ticket.getTicketType())
                )
                .build();
    }

    public static GetTicketResponseDto toGetTicketResponseDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketType ticketType = ticket.getTicketType();
        Event event = ticketType != null ? ticketType.getEvent() : null;

        return new GetTicketResponseDto(
                ticket.getId(),
                ticket.getStatus(),

                // TicketType
                ticketType != null ? ticketType.getPrice().doubleValue() : null,
                ticketType != null ? ticketType.getDescription() : null,

                // Event
                event != null ? event.getName() : null,
                event != null ? event.getVenue() : null,
                event != null ? event.getStart() : null,
                event != null ? event.getEnd() : null
        );
    }


    private static ListTicketTicketTypeResponseDto tolistTicketTicketTypeResponseDto(TicketType ticketType) {
        if (ticketType == null) {
            return null;
        }

        return ListTicketTicketTypeResponseDto.builder()
                .id(ticketType.getId())
                .name(ticketType.getName())
                .price(ticketType.getPrice())
                .build();
    }
}
