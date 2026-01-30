package com.devtiro.tickets.domain.dto.updatingEvent;

import com.devtiro.tickets.domain.enums.EventStatusEnum;
import com.devtiro.tickets.domain.dto.creatingEvent.CreateTicketTypeRequestDto;
import com.devtiro.tickets.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDto {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private User organizer;
    private List<UpdateTicketTypeRequestDto> ticketTypes = new ArrayList<>();

}
