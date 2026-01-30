package com.devtiro.tickets.controller;


;
import com.devtiro.tickets.domain.dto.ListingTickets.ListTicketResponseDto;
import com.devtiro.tickets.domain.dto.gettingTicket.GetTicketResponseDto;
import com.devtiro.tickets.domain.entity.Ticket;
import com.devtiro.tickets.mappers.TicketMapper;
import com.devtiro.tickets.repository.TicketRepository;
import com.devtiro.tickets.services.QrCodeService;
import com.devtiro.tickets.services.TicketService;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(path = "/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final QrCodeService qrCodeService;

    @GetMapping
    public Page<ListTicketResponseDto> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        Page<Ticket> tickets = ticketService.listTicketsForUser(userId, pageable);

        return tickets.map(TicketMapper::tolistTicketResponseDto);
    }

    @GetMapping("/{ticketId}")
    public GetTicketResponseDto getMyTicket(
            @PathVariable UUID ticketId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        Ticket ticket = ticketService
                .getTicketForUser(userId, ticketId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ticket not found"
                ));

        return TicketMapper.toGetTicketResponseDto(ticket);
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable UUID ticketId
    ) {
      byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(
          UUID.fromString(jwt.getSubject()),
          ticketId
      );

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_PNG);
      headers.setContentLength(qrCodeImage.length);

      return ResponseEntity.ok()
          .headers(headers)
          .body(qrCodeImage);
    }



}
