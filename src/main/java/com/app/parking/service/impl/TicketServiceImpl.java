package com.app.parking.service.impl;

import com.app.parking.domain.Ticket;
import com.app.parking.repository.TicketRepository;
import com.app.parking.service.TicketService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.app.parking.domain.Ticket}.
 */
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        LOG.debug("Request to save Ticket : {}", ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket update(Ticket ticket) {
        LOG.debug("Request to update Ticket : {}", ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> partialUpdate(Ticket ticket) {
        LOG.debug("Request to partially update Ticket : {}", ticket);

        return ticketRepository
            .findById(ticket.getId())
            .map(existingTicket -> {
                if (ticket.getTicketNo() != null) {
                    existingTicket.setTicketNo(ticket.getTicketNo());
                }
                if (ticket.getLastName() != null) {
                    existingTicket.setLastName(ticket.getLastName());
                }
                if (ticket.getFirstName() != null) {
                    existingTicket.setFirstName(ticket.getFirstName());
                }
                if (ticket.getPhone() != null) {
                    existingTicket.setPhone(ticket.getPhone());
                }
                if (ticket.getRoom() != null) {
                    existingTicket.setRoom(ticket.getRoom());
                }
                if (ticket.getArrivalDate() != null) {
                    existingTicket.setArrivalDate(ticket.getArrivalDate());
                }
                if (ticket.getDepartureDate() != null) {
                    existingTicket.setDepartureDate(ticket.getDepartureDate());
                }
                if (ticket.getModel() != null) {
                    existingTicket.setModel(ticket.getModel());
                }
                if (ticket.getColor() != null) {
                    existingTicket.setColor(ticket.getColor());
                }
                if (ticket.getLicensePlate() != null) {
                    existingTicket.setLicensePlate(ticket.getLicensePlate());
                }
                if (ticket.getStatus() != null) {
                    existingTicket.setStatus(ticket.getStatus());
                }
                if (ticket.getDamanged() != null) {
                    existingTicket.setDamanged(ticket.getDamanged());
                }
                if (ticket.getNote() != null) {
                    existingTicket.setNote(ticket.getNote());
                }

                return existingTicket;
            })
            .map(ticketRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ticket> findAll(Pageable pageable) {
        LOG.debug("Request to get all Tickets");
        return ticketRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findOne(Long id) {
        LOG.debug("Request to get Ticket : {}", id);
        return ticketRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Ticket : {}", id);
        ticketRepository.deleteById(id);
    }
}
