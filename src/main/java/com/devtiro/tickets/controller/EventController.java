package com.devtiro.tickets.controller;

import com.devtiro.tickets.domain.dto.creatingEvent.CreateEventRequestDto;
import com.devtiro.tickets.domain.dto.creatingEvent.CreateEventResponseDto;
import com.devtiro.tickets.domain.dto.gettingEvent.GetEventDetailsResponseDto;
import com.devtiro.tickets.domain.dto.listingEvents.ListEventResponseDto;
import com.devtiro.tickets.domain.dto.updatingEvent.UpdateEventRequestDto;
import com.devtiro.tickets.domain.dto.updatingEvent.UpdateEventResponseDto;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.domain.requests.CreateEventRequest;
import com.devtiro.tickets.domain.requests.UpdateEventRequest;
import com.devtiro.tickets.mappers.EventMapper;
import com.devtiro.tickets.services.EventService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/events")
public class EventController {

    @Autowired
    private final EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEventById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        Optional<Event> eventForOrganizer =
                eventService.getEventForOrganizer(UUID.fromString(jwt.getSubject()), eventId);

        return eventForOrganizer
                .map(event -> ResponseEntity.ok(EventMapper.toGetEventDetailsResponseDto(event)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


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

    @PutMapping("/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @RequestBody UpdateEventRequestDto updateEventRequestDto
    ) {
        UpdateEventRequest updateEventRequest =
                EventMapper.toUpdateEventRequest(updateEventRequestDto);

        Event updatedEvent = eventService.updateEventForOrganizer(
                UUID.fromString(jwt.getSubject()),
                eventId,
                updateEventRequest
        );

        return ResponseEntity.ok(
                EventMapper.toUpdateEventResponseDto(updatedEvent)
        );
    }


    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody CreateEventRequestDto createEventRequestDto
    ) {
        CreateEventRequest createEventRequest = EventMapper.toCreateEventRequest(createEventRequestDto);
        UUID organiserId = UUID.fromString(jwt.getSubject());

        Event createdEvent = eventService.createEvent(organiserId, createEventRequest);

        CreateEventResponseDto createEventResponseDto = EventMapper.toCreateEventResponseDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);

    }

    @DeleteMapping(path = "{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        eventService.deleteEventForOrganizer(UUID.fromString(jwt.getSubject()), eventId);

        return ResponseEntity.noContent().build();

    }



}
