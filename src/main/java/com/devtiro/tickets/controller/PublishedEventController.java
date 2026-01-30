package com.devtiro.tickets.controller;

import com.devtiro.tickets.domain.dto.gettingEvent.GetPublishedEventDetailsResponseDto;
import com.devtiro.tickets.domain.dto.listingEvents.ListPublishedEventsResponseDto;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.mappers.EventMapper;
import com.devtiro.tickets.services.EventService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;

    @GetMapping
    public Page<ListPublishedEventsResponseDto> listPublishedEvents(@RequestParam(required = false) String q, Pageable pageable) {

        Page<Event> events;
        if (q != null && !q.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);


        }
        return events.map(EventMapper::toListPublishedEventsResponseDto);

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails(
            @PathVariable UUID eventId
    ) {
        return eventService.getPublishedEvent(eventId)
                .map(event -> ResponseEntity.ok(
                        EventMapper.getPublishedEventDetailsResponseDto(event)
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}


