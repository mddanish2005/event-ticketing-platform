package com.devtiro.tickets.controller;

import com.devtiro.tickets.domain.dto.ticketValidation.TicketValidationRequestDto;
import com.devtiro.tickets.domain.dto.ticketValidation.TicketValidationResponseDto;
import com.devtiro.tickets.domain.entity.TicketValidation;
import com.devtiro.tickets.domain.enums.TicketValidationMethodEnum;
import com.devtiro.tickets.mappers.TicketValidationMapper;
import com.devtiro.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;


    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> getValidation(@RequestBody TicketValidationRequestDto ticketValidationRequestDto) {
        TicketValidationMethodEnum method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;
        if (TicketValidationMethodEnum.MANUAL.equals(method)) {
            ticketValidation = ticketValidationService.validateTicketManually(ticketValidationRequestDto.getId());
        } else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(ticketValidationRequestDto.getId());
        }


        return ResponseEntity.ok(TicketValidationMapper.toTicketValidationResponseDto(ticketValidation));
    }

}

