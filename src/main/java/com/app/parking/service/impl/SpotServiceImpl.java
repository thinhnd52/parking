package com.app.parking.service.impl;

import com.app.parking.domain.Spot;
import com.app.parking.repository.SpotRepository;
import com.app.parking.service.SpotService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.app.parking.domain.Spot}.
 */
@Service
@Transactional
public class SpotServiceImpl implements SpotService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotServiceImpl.class);

    private final SpotRepository spotRepository;

    public SpotServiceImpl(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Override
    public Spot save(Spot spot) {
        LOG.debug("Request to save Spot : {}", spot);
        return spotRepository.save(spot);
    }

    @Override
    public Spot update(Spot spot) {
        LOG.debug("Request to update Spot : {}", spot);
        return spotRepository.save(spot);
    }

    @Override
    public Optional<Spot> partialUpdate(Spot spot) {
        LOG.debug("Request to partially update Spot : {}", spot);

        return spotRepository
            .findById(spot.getId())
            .map(existingSpot -> {
                if (spot.getName() != null) {
                    existingSpot.setName(spot.getName());
                }
                if (spot.getSpotNo() != null) {
                    existingSpot.setSpotNo(spot.getSpotNo());
                }
                if (spot.getLevel() != null) {
                    existingSpot.setLevel(spot.getLevel());
                }
                if (spot.getSpotRow() != null) {
                    existingSpot.setSpotRow(spot.getSpotRow());
                }
                if (spot.getSpotColumn() != null) {
                    existingSpot.setSpotColumn(spot.getSpotColumn());
                }
                if (spot.getOccupied() != null) {
                    existingSpot.setOccupied(spot.getOccupied());
                }

                return existingSpot;
            })
            .map(spotRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Spot> findAll(Pageable pageable) {
        LOG.debug("Request to get all Spots");
        return spotRepository.findAll(pageable);
    }

    /**
     *  Get all the spots where Ticket is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Spot> findAllWhereTicketIsNull() {
        LOG.debug("Request to get all spots where Ticket is null");
        return StreamSupport.stream(spotRepository.findAll().spliterator(), false).filter(spot -> spot.getTicket() == null).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Spot> findOne(Long id) {
        LOG.debug("Request to get Spot : {}", id);
        return spotRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Spot : {}", id);
        spotRepository.deleteById(id);
    }
}
