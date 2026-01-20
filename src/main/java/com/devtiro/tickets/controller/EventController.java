package com.devtiro.tickets.controller;

import com.devtiro.tickets.domain.dto.CreateEventRequest;
import com.devtiro.tickets.domain.dto.CreateEventRequestDto;
import com.devtiro.tickets.domain.dto.CreateEventResponseDto;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.mappers.EventMapper;
import com.devtiro.tickets.services.EventService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@RestController
@RequestMapping(params = "/api/v1/events")
public class EventController {

    private final EventService eventService;


    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
           @AuthenticationPrincipal Jwt jwt,
           @RequestBody CreateEventRequestDto createEventRequestDto
    ){
        CreateEventRequest createEventRequest = EventMapper.toCreateEventRequest(createEventRequestDto);
        UUID organiserId = UUID.fromString(jwt.getSubject());

        Event createdEvent = eventService.createEvent(organiserId,createEventRequest);

        CreateEventResponseDto createEventResponseDto = EventMapper.toCreateEventResponseDto(createdEvent);
        return new  ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);

    }


}
