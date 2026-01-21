package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.dto.CreateEventRequest;
import com.devtiro.tickets.domain.dto.CreateTicketTypeRequest;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.domain.entity.TicketType;
import com.devtiro.tickets.domain.entity.User;
import com.devtiro.tickets.exception.UserNotFoundException;
import com.devtiro.tickets.repository.EventRepository;
import com.devtiro.tickets.repository.UserRepository;
import com.devtiro.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    
    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest) {
        User organizer = userRepository.findById(organizerId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("User with ID '%s' not found",organizerId)
                )
        );

        Event event = new Event();

        List<TicketType> ticketTypes = new ArrayList<>();
        List< CreateTicketTypeRequest> ticketTypeRequests = createEventRequest.getTicketTypes();
        for(CreateTicketTypeRequest ticketTypeRequest: ticketTypeRequests){
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

}
