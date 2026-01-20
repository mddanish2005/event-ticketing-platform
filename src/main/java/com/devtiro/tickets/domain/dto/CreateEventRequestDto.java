package com.devtiro.tickets.domain.dto;

import com.devtiro.tickets.domain.EventStatusEnum;
import com.devtiro.tickets.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDTO {
    private String name;
       private LocalDateTime start;
       private LocalDateTime end;
       private String venue;
       private LocalDateTime salesStartDate;
       private LocalDateTime salesEndDate;
       private String description;
       private EventStatusEnum status;
       private User organizer;

}
