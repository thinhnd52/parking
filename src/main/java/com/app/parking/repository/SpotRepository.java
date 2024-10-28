package com.app.parking.repository;

import com.app.parking.domain.Spot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Spot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {}
