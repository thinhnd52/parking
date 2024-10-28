package com.app.parking.service;

import com.app.parking.domain.Spot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.app.parking.domain.Spot}.
 */
public interface SpotService {
    /**
     * Save a spot.
     *
     * @param spot the entity to save.
     * @return the persisted entity.
     */
    Spot save(Spot spot);

    /**
     * Updates a spot.
     *
     * @param spot the entity to update.
     * @return the persisted entity.
     */
    Spot update(Spot spot);

    /**
     * Partially updates a spot.
     *
     * @param spot the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Spot> partialUpdate(Spot spot);

    /**
     * Get all the spots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Spot> findAll(Pageable pageable);

    /**
     * Get all the Spot where Ticket is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Spot> findAllWhereTicketIsNull();

    /**
     * Get the "id" spot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Spot> findOne(Long id);

    /**
     * Delete the "id" spot.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
