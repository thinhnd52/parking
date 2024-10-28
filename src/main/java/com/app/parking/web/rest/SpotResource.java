package com.app.parking.web.rest;

import com.app.parking.domain.Spot;
import com.app.parking.repository.SpotRepository;
import com.app.parking.service.SpotService;
import com.app.parking.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.app.parking.domain.Spot}.
 */
@RestController
@RequestMapping("/api/spots")
public class SpotResource {

    private static final Logger LOG = LoggerFactory.getLogger(SpotResource.class);

    private static final String ENTITY_NAME = "spot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpotService spotService;

    private final SpotRepository spotRepository;

    public SpotResource(SpotService spotService, SpotRepository spotRepository) {
        this.spotService = spotService;
        this.spotRepository = spotRepository;
    }

    /**
     * {@code POST  /spots} : Create a new spot.
     *
     * @param spot the spot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spot, or with status {@code 400 (Bad Request)} if the spot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Spot> createSpot(@Valid @RequestBody Spot spot) throws URISyntaxException {
        LOG.debug("REST request to save Spot : {}", spot);
        if (spot.getId() != null) {
            throw new BadRequestAlertException("A new spot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        spot = spotService.save(spot);
        return ResponseEntity.created(new URI("/api/spots/" + spot.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, spot.getId().toString()))
            .body(spot);
    }

    /**
     * {@code PUT  /spots/:id} : Updates an existing spot.
     *
     * @param id the id of the spot to save.
     * @param spot the spot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spot,
     * or with status {@code 400 (Bad Request)} if the spot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Spot> updateSpot(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Spot spot)
        throws URISyntaxException {
        LOG.debug("REST request to update Spot : {}, {}", id, spot);
        if (spot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        spot = spotService.update(spot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, spot.getId().toString()))
            .body(spot);
    }

    /**
     * {@code PATCH  /spots/:id} : Partial updates given fields of an existing spot, field will ignore if it is null
     *
     * @param id the id of the spot to save.
     * @param spot the spot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spot,
     * or with status {@code 400 (Bad Request)} if the spot is not valid,
     * or with status {@code 404 (Not Found)} if the spot is not found,
     * or with status {@code 500 (Internal Server Error)} if the spot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Spot> partialUpdateSpot(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Spot spot
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Spot partially : {}, {}", id, spot);
        if (spot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Spot> result = spotService.partialUpdate(spot);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, spot.getId().toString())
        );
    }

    /**
     * {@code GET  /spots} : get all the spots.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spots in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Spot>> getAllSpots(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("ticket-is-null".equals(filter)) {
            LOG.debug("REST request to get all Spots where ticket is null");
            return new ResponseEntity<>(spotService.findAllWhereTicketIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of Spots");
        Page<Spot> page = spotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /spots/:id} : get the "id" spot.
     *
     * @param id the id of the spot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Spot> getSpot(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Spot : {}", id);
        Optional<Spot> spot = spotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spot);
    }

    /**
     * {@code DELETE  /spots/:id} : delete the "id" spot.
     *
     * @param id the id of the spot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpot(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Spot : {}", id);
        spotService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
