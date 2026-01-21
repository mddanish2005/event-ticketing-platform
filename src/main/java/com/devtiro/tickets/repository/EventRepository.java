package com.devtiro.tickets.repository;

import com.devtiro.tickets.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

   Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);
   Optional<Event> findByIdAndOrganizerId(UUID id, UUID organizerId);

}
