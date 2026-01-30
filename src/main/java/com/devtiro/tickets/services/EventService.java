package com.devtiro.tickets.services;


import com.devtiro.tickets.domain.requests.CreateEventRequest;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.domain.requests.UpdateEventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface EventService {

    Event createEvent(UUID organiserId,CreateEventRequest event);
    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);
    Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId);
    Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest updateEventRequest);
    void deleteEventForOrganizer(UUID organizerId, UUID id);
    Page<Event> listPublishedEvents(Pageable pageable);
    Page<Event> searchPublishedEvents(String query , Pageable pageable);
    Optional<Event> getPublishedEvent(UUID id);

}
