package com.app.parking.service;

import com.app.parking.domain.Driver;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.app.parking.domain.Driver}.
 */
public interface DriverService {
    /**
     * Save a driver.
     *
     * @param driver the entity to save.
     * @return the persisted entity.
     */
    Driver save(Driver driver);

    /**
     * Updates a driver.
     *
     * @param driver the entity to update.
     * @return the persisted entity.
     */
    Driver update(Driver driver);

    /**
     * Partially updates a driver.
     *
     * @param driver the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Driver> partialUpdate(Driver driver);

    /**
     * Get all the drivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Driver> findAll(Pageable pageable);

    /**
     * Get all the Driver where Ticket is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Driver> findAllWhereTicketIsNull();

    /**
     * Get the "id" driver.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Driver> findOne(Long id);

    /**
     * Delete the "id" driver.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
