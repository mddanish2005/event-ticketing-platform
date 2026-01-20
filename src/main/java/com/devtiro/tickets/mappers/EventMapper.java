package com.devtiro.tickets.mappers;

import com.devtiro.tickets.domain.dto.*;
import com.devtiro.tickets.domain.entity.Event;
import com.devtiro.tickets.domain.entity.TicketType;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class EventMapper {

    private EventMapper(){

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
                   .salesStartDate(dto.getSalesStartDate())
                   .salesEndDate(dto.getSalesEndDate())
                   .status(dto.getStatus())
                   .ticketTypes(
                           toTicketTypeRequests(dto.getTicketTypes())
                   )
                   .build();
       }

       public static List<CreateTicketTypeResponseDto> toTicketTypeResponseDtos(
               List<TicketType> ticketTypes
       ){
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
                                      .build()
                      )
                      .collect(Collectors.toList());
       }

       public static CreateEventResponseDto toCreateEventResponseDto(
               Event event
       ){
        return CreateEventResponseDto.builder()
                .name(event.getName())
                .start(event.getStart())
                .end(event.getEnd())
                .venue(event.getVenue())
                .salesStartDate(event.getSalesStartDate())
                .salesEndDate(event.getSalesEndDate())
                .description(event.getDescription())
                .status(event.getStatus())
                .organizer(
                        UserResponseDto.builder()
                                .id(event.getOrganiser().getId())
                                .name(event.getOrganiser().getName())
                                .build()
                )
                .ticketTypes(
                        toTicketTypeResponseDtos(event.getTicketTypes())
                )
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();



       }

}
