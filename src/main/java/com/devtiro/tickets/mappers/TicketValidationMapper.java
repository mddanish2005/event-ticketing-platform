package com.devtiro.tickets.mappers;

import com.devtiro.tickets.domain.dto.ticketValidation.TicketValidationResponseDto;
import com.devtiro.tickets.domain.entity.TicketValidation;

public class TicketValidationMapper {

    public static TicketValidationResponseDto toTicketValidationResponseDto(
            TicketValidation ticketValidation
    ) {
        if (ticketValidation == null) {
            return null;
        }

        return new TicketValidationResponseDto(
                ticketValidation.getTicket().getId(),
                ticketValidation.getStatus()
        );
    }
}
