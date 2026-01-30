package com.devtiro.tickets.domain.dto.gettingEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPublishedEventTicketTypeResponseDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
}
