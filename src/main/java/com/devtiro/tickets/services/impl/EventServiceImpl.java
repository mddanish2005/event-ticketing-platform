package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.enums.EventStatusEnum;
import com.devtiro.tickets.domain.requests.CreateEventRequest;
import com.devtiro.tickets.domain.requests.CreateTicketTypeRequest;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.domain.entity.TicketType;
import com.devtiro.tickets.domain.entity.User;
import com.devtiro.tickets.domain.requests.UpdateEventRequest;
import com.devtiro.tickets.domain.requests.UpdateTicketTypeRequest;
import com.devtiro.tickets.exception.EventNotFoundException;
import com.devtiro.tickets.exception.EventUpdateException;
import com.devtiro.tickets.exception.TicketTypeNotFoundException;
import com.devtiro.tickets.exception.UserNotFoundException;
import com.devtiro.tickets.repository.EventRepository;
import com.devtiro.tickets.repository.UserRepository;
import com.devtiro.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest) {
        User organizer = userRepository.findById(organizerId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)
                )
        );

        Event event = new Event();

        List<TicketType> ticketTypes = new ArrayList<>();
        List<CreateTicketTypeRequest> ticketTypeRequests = createEventRequest.getTicketTypes();
        for (CreateTicketTypeRequest ticketTypeRequest : ticketTypeRequests) {
            TicketType ticketType = new TicketType();
            ticketType.setName(ticketTypeRequest.getName());
            ticketType.setPrice(ticketTypeRequest.getPrice());
            ticketType.setEvent(event);
            ticketType.setTotalAvailable(ticketTypeRequest.getTotalAvailable());
            ticketType.setDescription(ticketTypeRequest.getDescription());
            ticketTypes.add(ticketType);
        }


        event.setName(createEventRequest.getName());
        event.setStart(createEventRequest.getStart());
        event.setEnd(createEventRequest.getEnd());
        event.setVenue(createEventRequest.getVenue());
        event.setSalesStart(createEventRequest.getSalesStart());
        event.setSalesEnd(createEventRequest.getSalesEnd());
        event.setStatus(createEventRequest.getStatus());
        event.setOrganizer(organizer);
        event.setTicketTypes(ticketTypes);

        return eventRepository.save(event);


    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {

        return eventRepository.findByOrganizerId(organizerId, pageable);

    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
    }

    @Transactional
    @Override
    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest updateEventRequest) {
        if (updateEventRequest.getId() == null) {
            throw new EventUpdateException("Id cannot be null");
        }
        if (!id.equals(updateEventRequest.getId())) {
            throw new EventUpdateException("Cannot update id of an event");
        }
        Event existingEvent = eventRepository.findByIdAndOrganizerId(id, organizerId).orElseThrow(
                () -> new EventNotFoundException(
                        String.format("Event with ID '%s' does not exist", id)
                )
        );
        existingEvent.setName(updateEventRequest.getName());
        existingEvent.setStart(updateEventRequest.getStart());
        existingEvent.setEnd(updateEventRequest.getEnd());
        existingEvent.setVenue(updateEventRequest.getVenue());
        existingEvent.setSalesStart(updateEventRequest.getSalesStart());
        existingEvent.setSalesEnd(updateEventRequest.getSalesEnd());
        existingEvent.setStatus(updateEventRequest.getStatus());

        Set<UUID> requestTicketTypeIds = updateEventRequest.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(
                existingTicketType -> !requestTicketTypeIds.contains(existingTicketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for (UpdateTicketTypeRequest ticketType : updateEventRequest.getTicketTypes()) {
            if (null == ticketType.getId()) {
                // Create
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);

            } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
                // Update
                TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            } else {
                throw new TicketTypeNotFoundException(String.format(
                        "Ticket type with ID '%s' does not exist", ticketType.getId()
                ));
            }
        }

        return eventRepository.save(existingEvent);


    }

    @Transactional
    @Override
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {

        getEventForOrganizer(organizerId, id).ifPresent(eventRepository::delete);

    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
       return eventRepository.findByStatus(EventStatusEnum.PUBLISHED,pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return  eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
        return eventRepository.findByIdAndStatus(id,EventStatusEnum.PUBLISHED);
    }


}
