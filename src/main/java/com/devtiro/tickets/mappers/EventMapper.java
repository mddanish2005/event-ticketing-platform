package com.devtiro.tickets.mappers;

import com.devtiro.tickets.domain.dto.*;
import com.devtiro.tickets.domain.dto.creatingEvent.CreateEventRequestDto;
import com.devtiro.tickets.domain.dto.creatingEvent.CreateEventResponseDto;
import com.devtiro.tickets.domain.dto.creatingEvent.CreateTicketTypeRequestDto;
import com.devtiro.tickets.domain.dto.creatingEvent.CreateTicketTypeResponseDto;
import com.devtiro.tickets.domain.dto.gettingEvent.GetEventDetailsResponseDto;
import com.devtiro.tickets.domain.dto.gettingEvent.GetEventTicketTypeResponseDto;
import com.devtiro.tickets.domain.dto.listingEvents.ListEventResponseDto;
import com.devtiro.tickets.domain.dto.listingEvents.ListEventTicketTypeResponseDto;
import com.devtiro.tickets.domain.dto.updatingEvent.UpdateEventRequestDto;
import com.devtiro.tickets.domain.dto.updatingEvent.UpdateEventResponseDto;
import com.devtiro.tickets.domain.dto.updatingEvent.UpdateTicketTypeRequestDto;
import com.devtiro.tickets.domain.dto.updatingEvent.UpdateTicketTypeResponseDto;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.domain.entity.TicketType;
import com.devtiro.tickets.domain.requests.CreateEventRequest;
import com.devtiro.tickets.domain.requests.CreateTicketTypeRequest;
import com.devtiro.tickets.domain.requests.UpdateEventRequest;
import com.devtiro.tickets.domain.requests.UpdateTicketTypeRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class EventMapper {

    private EventMapper() {

    }

    private static List<CreateTicketTypeRequest> toTicketTypeRequests(
            List<CreateTicketTypeRequestDto> dtos
    ) {
        if (dtos == null) {
            return List.of();
        }

        return dtos.stream()
                .map(dto ->
                        CreateTicketTypeRequest.builder()
                                .name(dto.getName())
                                .price(dto.getPrice())
                                .totalAvailable(dto.getTotalAvailable())
                                .description(dto.getDescription())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static CreateEventRequest toCreateEventRequest(
            CreateEventRequestDto dto
    ) {
        return CreateEventRequest.builder()
                .name(dto.getName())
                .venue(dto.getVenue())
                .start(dto.getStart())
                .end(dto.getEnd())
                .salesStart(dto.getSalesStart())
                .salesEnd(dto.getSalesEnd())
                .status(dto.getStatus())
                .ticketTypes(
                        toTicketTypeRequests(dto.getTicketTypes())
                )
                .build();
    }

    public static List<CreateTicketTypeResponseDto> toTicketTypeResponseDtos(
            List<TicketType> ticketTypes
    ) {
        if (ticketTypes == null) {
            return List.of();
        }

        return ticketTypes.stream()
                .map(ticketType ->
                        CreateTicketTypeResponseDto.builder()
                                .name(ticketType.getName())
                                .price(ticketType.getPrice())
                                .totalAvailable(ticketType.getTotalAvailable())
                                .description(ticketType.getDescription())
                                .createdAt(ticketType.getCreatedAt())
                                .updatedAt(ticketType.getUpdatedAt())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static CreateEventResponseDto toCreateEventResponseDto(
            Event event
    ) {
        return CreateEventResponseDto.builder()
                .name(event.getName())
                .start(event.getStart())
                .end(event.getEnd())
                .venue(event.getVenue())
                .salesStart(event.getSalesStart())
                .salesEnd(event.getSalesEnd())
                .status(event.getStatus())
                .organizer(
                        UserResponseDto.builder()
                                .id(event.getOrganizer().getId())
                                .name(event.getOrganizer().getName())
                                .build()
                )
                .ticketTypes(
                        toTicketTypeResponseDtos(event.getTicketTypes())
                )
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();


    }

    public static List<ListEventTicketTypeResponseDto> toListEventTicketTypeResponseDto(
            List<TicketType> ticketTypes
    ) {
        if (ticketTypes == null) {
            return List.of();
        }

        return ticketTypes.stream()
                .map(ticketType ->
                        ListEventTicketTypeResponseDto.builder()
                                .name(ticketType.getName())
                                .price(ticketType.getPrice())
                                .totalAvailable(ticketType.getTotalAvailable())
                                .description(ticketType.getDescription())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static ListEventResponseDto toListEventResponseDto(Event event) {
        return ListEventResponseDto.builder()
                .id(event.getId())
                .name(event.getName())
                .venue(event.getVenue())
                .start(event.getStart())
                .end(event.getEnd())
                .salesStart(event.getSalesStart())
                .salesEnd(event.getSalesEnd())
                .status(event.getStatus())
                .ticketTypes(
                        EventMapper.toListEventTicketTypeResponseDto(event.getTicketTypes())
                )
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    public static GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event) {
        if (event == null) {
            return null;
        }

        GetEventDetailsResponseDto dto = new GetEventDetailsResponseDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setStart(event.getStart());
        dto.setEnd(event.getEnd());
        dto.setVenue(event.getVenue());
        dto.setSalesStart(event.getSalesStart());
        dto.setSalesEnd(event.getSalesEnd());
        dto.setStatus(event.getStatus());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setUpdatedAt(event.getUpdatedAt());
        dto.setTicketTypes(toTicketTypeDtos(event.getTicketTypes()));

        return dto;
    }

    // Nested mapper for ticket types
    private static List<GetEventTicketTypeResponseDto> toTicketTypeDtos(List<TicketType> ticketTypes) {
        if (ticketTypes == null) {
            return List.of();
        }

        return ticketTypes.stream()
                .map(ticketType -> {
                    GetEventTicketTypeResponseDto dto = new GetEventTicketTypeResponseDto();
                    dto.setId(ticketType.getId());
                    dto.setName(ticketType.getName());
                    dto.setPrice(ticketType.getPrice());
                    dto.setTotalAvailable(ticketType.getTotalAvailable());
                    dto.setDescription(ticketType.getDescription());
                    dto.setCreatedAt(ticketType.getCreatedAt());
                    dto.setUpdatedAt(ticketType.getUpdatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }


public static UpdateEventRequest toUpdateEventRequest(UpdateEventRequestDto dto) {
       if (dto == null) return null;

       UpdateEventRequest req = new UpdateEventRequest();
       req.setId(dto.getId());
       req.setName(dto.getName());
       req.setStart(dto.getStart());
       req.setEnd(dto.getEnd());
       req.setVenue(dto.getVenue());
       req.setSalesStart(dto.getSalesStart());
       req.setSalesEnd(dto.getSalesEnd());
       req.setStatus(dto.getStatus());
       req.setOrganizer(dto.getOrganizer());

       req.setTicketTypes(
               dto.getTicketTypes()
                  .stream()
                  .map(EventMapper::toUpdateTicketTypeRequest)
                  .toList()
       );

       return req;
   }

    private static UpdateTicketTypeRequest toUpdateTicketTypeRequest(UpdateTicketTypeRequestDto dto) {
        UpdateTicketTypeRequest req = new UpdateTicketTypeRequest();
        req.setId(dto.getId());
        req.setName(dto.getName());
        req.setPrice(dto.getPrice());
        req.setTotalAvailable(dto.getTotalAvailable());
        req.setDescription(dto.getDescription());
        return req;
    }
    public static UpdateEventResponseDto toUpdateEventResponseDto(Event event) {
        return UpdateEventResponseDto.builder()
                .id(event.getId())
                .name(event.getName())
                .start(event.getStart())
                .end(event.getEnd())
                .venue(event.getVenue())
                .salesStart(event.getSalesStart())
                .salesEnd(event.getSalesEnd())
                .status(event.getStatus())
                .organizer(
                    UserResponseDto.builder()
                        .id(event.getOrganizer().getId())
                        .name(event.getOrganizer().getName())
                        .build()
                )
                .ticketTypes(
                    event.getTicketTypes()
                         .stream()
                         .map(EventMapper::toUpdateTicketTypeResponseDto)
                         .toList()
                )
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    private static UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType t) {
        return UpdateTicketTypeResponseDto.builder()
                .id(t.getId())
                .name(t.getName())
                .price(t.getPrice())
                .totalAvailable(t.getTotalAvailable())
                .description(t.getDescription())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }



}
