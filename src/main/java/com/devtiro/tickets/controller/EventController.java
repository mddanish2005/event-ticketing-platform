package com.devtiro.tickets.controller;

import com.devtiro.tickets.domain.dto.CreateEventRequest;
import com.devtiro.tickets.domain.dto.CreateEventRequestDto;
import com.devtiro.tickets.domain.dto.CreateEventResponseDto;
import com.devtiro.tickets.domain.dto.ListEventResponseDto;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.mappers.EventMapper;
import com.devtiro.tickets.services.EventService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/events")
public class EventController {

    @Autowired
    private final EventService eventService;

    @GetMapping
        public ResponseEntity<Page<ListEventResponseDto>> ListEvents(
                @AuthenticationPrincipal Jwt jwt,
                Pageable pageable
    ) {
        Page<Event> events =
                eventService.listEventsForOrganizer(UUID.fromString(jwt.getSubject()), pageable);

        Page<ListEventResponseDto> eventResponseDtos =
                events.map(EventMapper::toListEventResponseDto);

        return ResponseEntity.ok(eventResponseDtos);

    }


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
