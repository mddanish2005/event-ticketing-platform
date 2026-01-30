package com.devtiro.tickets.domain.dto.ticketValidation;

import java.util.UUID;

import com.devtiro.tickets.domain.enums.TicketValidationMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDto {
  private UUID id;
  private TicketValidationMethodEnum method;
}
