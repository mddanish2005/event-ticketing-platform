package com.devtiro.tickets.domain.dto.updatingEvent;

import com.devtiro.tickets.domain.enums.EventStatusEnum;
import com.devtiro.tickets.domain.dto.UserResponseDto;
import com.devtiro.tickets.domain.dto.creatingEvent.CreateTicketTypeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventResponseDto {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private UserResponseDto organizer;
    private List<UpdateTicketTypeResponseDto> ticketTypes = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
