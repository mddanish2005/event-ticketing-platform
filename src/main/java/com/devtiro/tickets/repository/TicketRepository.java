package com.devtiro.tickets.repository;

import com.devtiro.tickets.domain.entity.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,UUID> {

    int countByTicketTypeId(UUID ticketTypeId);
    Page<Ticket> findByPurchaserId(UUID purchaserId, Pageable pageable);
    @Query("""
         SELECT t FROM Ticket t
         JOIN FETCH t.ticketType tt
         JOIN FETCH tt.event
         WHERE t.id = :ticketId
         AND t.purchaser.id = :purchaserId
     """)
     Optional<Ticket> findByIdAndPurchaserIdWithDetails(
             @Param("ticketId") UUID ticketId,
             @Param("purchaserId") UUID purchaserId
     );
}
