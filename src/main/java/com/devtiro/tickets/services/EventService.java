package com.devtiro.tickets.services;


import com.devtiro.tickets.domain.dto.CreateEventRequest;
import com.devtiro.tickets.domain.entity.Event;

import java.util.UUID;

public interface EventService {

    Event createEvent(UUID organiserId,CreateEventRequest event);


}
