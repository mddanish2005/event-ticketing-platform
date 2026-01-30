package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.entity.QrCode;
import com.devtiro.tickets.domain.entity.Ticket;
import com.devtiro.tickets.domain.entity.TicketType;
import com.devtiro.tickets.domain.entity.User;
import com.devtiro.tickets.domain.enums.TicketStatusEnum;
import com.devtiro.tickets.exception.TicketSoldOutException;
import com.devtiro.tickets.exception.TicketTypeNotFoundException;
import com.devtiro.tickets.exception.UserNotFoundException;
import com.devtiro.tickets.repository.TicketRepository;
import com.devtiro.tickets.repository.TicketTypeRepository;
import com.devtiro.tickets.repository.UserRepository;
import com.devtiro.tickets.services.QrCodeService;
import com.devtiro.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;
    private final TicketRepository ticketRepository;

    @Transactional
    @Override
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with %s id does not exist"));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(String.format("Ticket type with id %s not found", ticketTypeId)));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());

        if (purchasedTickets + 1 > ticketType.getTotalAvailable()) {


            throw new TicketSoldOutException();

        }


        Ticket ticket = new Ticket();
        ticket.setPurchaser(user);
        ticket.setTicketType(ticketType);
        ticket.setStatus(TicketStatusEnum.PURCHASED);

        Ticket savedTicket = ticketRepository.save(ticket);

        QrCode qrCode = qrCodeService.generateQrCode(savedTicket);
        qrCode.setTicket(savedTicket);


        return ticketRepository.save(savedTicket);


    }
}
