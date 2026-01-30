package com.devtiro.tickets.domain.dto.gettingEvent;

import com.devtiro.tickets.domain.enums.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPublishedEventDetailsResponseDto {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private List<GetPublishedEventTicketTypeResponseDto> ticketTypes = new ArrayList<>();
}
