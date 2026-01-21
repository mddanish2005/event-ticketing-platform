package com.devtiro.tickets.domain.requests;

import com.devtiro.tickets.domain.EventStatusEnum;
import com.devtiro.tickets.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventRequest {

    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private String description;
    private EventStatusEnum status;
    private User organizer;
    private List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();

}
