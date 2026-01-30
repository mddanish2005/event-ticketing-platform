package com.devtiro.tickets.controller;


import com.devtiro.tickets.domain.entity.TicketType;
import com.devtiro.tickets.services.TicketTypeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @PostMapping(path = "/{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchaseTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId
    ){
        ticketTypeService.purchaseTicket(UUID.fromString(jwt.getSubject()),ticketTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
