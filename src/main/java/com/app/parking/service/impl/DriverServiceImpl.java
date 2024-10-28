package com.app.parking.service.impl;

import com.app.parking.domain.Driver;
import com.app.parking.repository.DriverRepository;
import com.app.parking.service.DriverService;
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
 * Service Implementation for managing {@link com.app.parking.domain.Driver}.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private static final Logger LOG = LoggerFactory.getLogger(DriverServiceImpl.class);

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver save(Driver driver) {
        LOG.debug("Request to save Driver : {}", driver);
        return driverRepository.save(driver);
    }

    @Override
    public Driver update(Driver driver) {
        LOG.debug("Request to update Driver : {}", driver);
        return driverRepository.save(driver);
    }

    @Override
    public Optional<Driver> partialUpdate(Driver driver) {
        LOG.debug("Request to partially update Driver : {}", driver);

        return driverRepository
            .findById(driver.getId())
            .map(existingDriver -> {
                if (driver.getLastName() != null) {
                    existingDriver.setLastName(driver.getLastName());
                }
                if (driver.getFirstName() != null) {
                    existingDriver.setFirstName(driver.getFirstName());
                }
                if (driver.getPhone() != null) {
                    existingDriver.setPhone(driver.getPhone());
                }
                if (driver.getLicenseNo() != null) {
                    existingDriver.setLicenseNo(driver.getLicenseNo());
                }

                return existingDriver;
            })
            .map(driverRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Driver> findAll(Pageable pageable) {
        LOG.debug("Request to get all Drivers");
        return driverRepository.findAll(pageable);
    }

    /**
     *  Get all the drivers where Ticket is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Driver> findAllWhereTicketIsNull() {
        LOG.debug("Request to get all drivers where Ticket is null");
        return StreamSupport.stream(driverRepository.findAll().spliterator(), false).filter(driver -> driver.getTicket() == null).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Driver> findOne(Long id) {
        LOG.debug("Request to get Driver : {}", id);
        return driverRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Driver : {}", id);
        driverRepository.deleteById(id);
    }
}
