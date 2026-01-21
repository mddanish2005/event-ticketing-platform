package com.devtiro.tickets.domain.dto.creatingEvent;

import com.devtiro.tickets.domain.EventStatusEnum;
import com.devtiro.tickets.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {
       private String name;
       private LocalDateTime start;
       private LocalDateTime end;
       private String venue;
       private LocalDateTime salesStart;
       private LocalDateTime salesEnd;
       private EventStatusEnum status;
       private User organizer;
       private List<CreateTicketTypeRequestDto> ticketTypes = new ArrayList<>();

}
