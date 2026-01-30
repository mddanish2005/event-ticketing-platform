package com.devtiro.tickets.controller;

import com.devtiro.tickets.domain.dto.ticketValidation.TicketValidationRequestDto;
import com.devtiro.tickets.domain.entity.Ticket;
import com.devtiro.tickets.domain.entity.TicketValidation;
import com.devtiro.tickets.domain.enums.TicketValidationMethodEnum;
import com.devtiro.tickets.domain.enums.TicketValidationStatusEnum;
import com.devtiro.tickets.services.TicketValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketValidationService ticketValidationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "STAFF")
    public void testValidateTicketManually_Success() throws Exception {
        UUID ticketId = UUID.randomUUID();
        TicketValidationRequestDto requestDto = new TicketValidationRequestDto(ticketId,
                TicketValidationMethodEnum.MANUAL);

        Ticket ticket = Ticket.builder().id(ticketId).build();
        TicketValidation ticketValidation = TicketValidation.builder()
                .ticket(ticket)
                .status(TicketValidationStatusEnum.VALID)
                .build();

        when(ticketValidationService.validateTicketManually(any(UUID.class))).thenReturn(ticketValidation);

        mockMvc.perform(post("/api/v1/ticket-validations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(ticketId.toString()))
                .andExpect(jsonPath("$.status").value("VALID"));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void testValidateTicketByQrCode_Success() throws Exception {
        UUID qrCodeId = UUID.randomUUID();
        TicketValidationRequestDto requestDto = new TicketValidationRequestDto(qrCodeId,
                TicketValidationMethodEnum.QR_SCAN);

        Ticket ticket = Ticket.builder().id(UUID.randomUUID()).build();
        TicketValidation ticketValidation = TicketValidation.builder()
                .ticket(ticket)
                .status(TicketValidationStatusEnum.VALID)
                .build();

        when(ticketValidationService.validateTicketByQrCode(any(UUID.class))).thenReturn(ticketValidation);

        mockMvc.perform(post("/api/v1/ticket-validations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(ticket.getId().toString()))
                .andExpect(jsonPath("$.status").value("VALID"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testValidateTicket_ForbiddenForUser() throws Exception {
        TicketValidationRequestDto requestDto = new TicketValidationRequestDto(UUID.randomUUID(),
                TicketValidationMethodEnum.MANUAL);

        mockMvc.perform(post("/api/v1/ticket-validations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testValidateTicket_Unauthorized() throws Exception {
        TicketValidationRequestDto requestDto = new TicketValidationRequestDto(UUID.randomUUID(),
                TicketValidationMethodEnum.MANUAL);

        mockMvc.perform(post("/api/v1/ticket-validations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized());
    }
}
