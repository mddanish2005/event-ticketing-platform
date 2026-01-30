package com.devtiro.tickets.services;

import com.devtiro.tickets.domain.entity.QrCode;
import com.devtiro.tickets.domain.entity.Ticket;
import org.springframework.stereotype.Service;

@Service
public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
