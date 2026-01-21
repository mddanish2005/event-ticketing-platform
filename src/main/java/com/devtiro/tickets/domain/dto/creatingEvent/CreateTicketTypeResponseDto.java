package com.devtiro.tickets.domain.dto.creatingEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketTypeResponseDto {
      private String name;
      private BigDecimal price;
      private Integer totalAvailable;
      private String description;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;
}
