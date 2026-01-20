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
    public Event createEvent(UUID organiserId, CreateEventRequest createEventRequest) {
        User organiser = userRepository.findById(organiserId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("User with ID '%s' not found",organiserId)
                )
        );


        List<TicketType> ticketTypes = new ArrayList<>();
        List< CreateTicketTypeRequest> ticketTypeRequests = createEventRequest.getTicketTypes();
        for(CreateTicketTypeRequest ticketTypeRequest: ticketTypeRequests){
            TicketType ticketType = new TicketType();
            ticketType.setName(ticketTypeRequest.getName());
            ticketType.setPrice(ticketTypeRequest.getPrice());
            ticketType.setTotalAvailable(ticketTypeRequest.getTotalAvailable());
            ticketType.setDescription(ticketTypeRequest.getDescription());
            ticketTypes.add(ticketType);
        }

        Event event = new Event();
        event.setName(createEventRequest.getName());
        event.setStart(createEventRequest.getStart());
        event.setVenue(createEventRequest.getVenue());
        event.setSalesStartDate(createEventRequest.getSalesStartDate());
        event.setSalesEndDate(createEventRequest.getSalesEndDate());
        event.setDescription(createEventRequest.getDescription());
        event.setStatus(createEventRequest.getStatus());
        event.setOrganiser(organiser);
        event.setTicketTypes(ticketTypes);

        return eventRepository.save(event);


    }
}
