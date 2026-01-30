package com.devtiro.tickets.repository;

import com.devtiro.tickets.domain.entity.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {

    @Query("""
        SELECT tv FROM TicketValidation tv
        JOIN FETCH tv.ticket t
        WHERE tv.id = :id
    """)
    Optional<TicketValidation> findByIdWithTicket(UUID id);

}
