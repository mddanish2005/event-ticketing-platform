package com.devtiro.tickets.domain.dto.ticketValidation;

import java.util.UUID;

import com.devtiro.tickets.domain.enums.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketValidationResponseDto {
  private UUID ticketId;
  private TicketValidationStatusEnum status;
}
