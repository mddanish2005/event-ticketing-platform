package com.devtiro.tickets.repository;

import com.devtiro.tickets.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    public Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);

}
