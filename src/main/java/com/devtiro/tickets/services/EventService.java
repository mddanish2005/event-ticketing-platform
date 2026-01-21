package com.devtiro.tickets.services;


import com.devtiro.tickets.domain.dto.CreateEventRequest;
import com.devtiro.tickets.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EventService {

    Event createEvent(UUID organiserId,CreateEventRequest event);
    Page<Event> listEventsForOrganizer(UUID organiserId, Pageable pageable);


}
