package com.app.parking.web.rest;

import static com.app.parking.domain.SpotAsserts.*;
import static com.app.parking.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.parking.IntegrationTest;
import com.app.parking.domain.Spot;
import com.app.parking.repository.SpotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpotResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpotResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPOT_NO = "AAAAAAAAAA";
    private static final String UPDATED_SPOT_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_SPOT_ROW = 1;
    private static final Integer UPDATED_SPOT_ROW = 2;

    private static final Integer DEFAULT_SPOT_COLUMN = 1;
    private static final Integer UPDATED_SPOT_COLUMN = 2;

    private static final Boolean DEFAULT_OCCUPIED = false;
    private static final Boolean UPDATED_OCCUPIED = true;

    private static final String ENTITY_API_URL = "/api/spots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpotMockMvc;

    private Spot spot;

    private Spot insertedSpot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spot createEntity() {
        return new Spot()
            .name(DEFAULT_NAME)
            .spotNo(DEFAULT_SPOT_NO)
            .level(DEFAULT_LEVEL)
            .spotRow(DEFAULT_SPOT_ROW)
            .spotColumn(DEFAULT_SPOT_COLUMN)
            .occupied(DEFAULT_OCCUPIED);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spot createUpdatedEntity() {
        return new Spot()
            .name(UPDATED_NAME)
            .spotNo(UPDATED_SPOT_NO)
            .level(UPDATED_LEVEL)
            .spotRow(UPDATED_SPOT_ROW)
            .spotColumn(UPDATED_SPOT_COLUMN)
            .occupied(UPDATED_OCCUPIED);
    }

    @BeforeEach
    public void initTest() {
        spot = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSpot != null) {
            spotRepository.delete(insertedSpot);
            insertedSpot = null;
        }
    }

    @Test
    @Transactional
    void createSpot() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Spot
        var returnedSpot = om.readValue(
            restSpotMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spot)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Spot.class
        );

        // Validate the Spot in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSpotUpdatableFieldsEquals(returnedSpot, getPersistedSpot(returnedSpot));

        insertedSpot = returnedSpot;
    }

    @Test
    @Transactional
    void createSpotWithExistingId() throws Exception {
        // Create the Spot with an existing ID
        spot.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpotMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spot)))
            .andExpect(status().isBadRequest());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        spot.setName(null);

        // Create the Spot, which fails.

        restSpotMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spot)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSpots() throws Exception {
        // Initialize the database
        insertedSpot = spotRepository.saveAndFlush(spot);

        // Get all the spotList
        restSpotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spot.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].spotNo").value(hasItem(DEFAULT_SPOT_NO)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].spotRow").value(hasItem(DEFAULT_SPOT_ROW)))
            .andExpect(jsonPath("$.[*].spotColumn").value(hasItem(DEFAULT_SPOT_COLUMN)))
            .andExpect(jsonPath("$.[*].occupied").value(hasItem(DEFAULT_OCCUPIED.booleanValue())));
    }

    @Test
    @Transactional
    void getSpot() throws Exception {
        // Initialize the database
        insertedSpot = spotRepository.saveAndFlush(spot);

        // Get the spot
        restSpotMockMvc
            .perform(get(ENTITY_API_URL_ID, spot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(spot.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.spotNo").value(DEFAULT_SPOT_NO))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.spotRow").value(DEFAULT_SPOT_ROW))
            .andExpect(jsonPath("$.spotColumn").value(DEFAULT_SPOT_COLUMN))
            .andExpect(jsonPath("$.occupied").value(DEFAULT_OCCUPIED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSpot() throws Exception {
        // Get the spot
        restSpotMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpot() throws Exception {
        // Initialize the database
        insertedSpot = spotRepository.saveAndFlush(spot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the spot
        Spot updatedSpot = spotRepository.findById(spot.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSpot are not directly saved in db
        em.detach(updatedSpot);
        updatedSpot
            .name(UPDATED_NAME)
            .spotNo(UPDATED_SPOT_NO)
            .level(UPDATED_LEVEL)
            .spotRow(UPDATED_SPOT_ROW)
            .spotColumn(UPDATED_SPOT_COLUMN)
            .occupied(UPDATED_OCCUPIED);

        restSpotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpot.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSpot))
            )
            .andExpect(status().isOk());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSpotToMatchAllProperties(updatedSpot);
    }

    @Test
    @Transactional
    void putNonExistingSpot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spot.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, spot.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(spot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(spot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpotMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spot)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpotWithPatch() throws Exception {
        // Initialize the database
        insertedSpot = spotRepository.saveAndFlush(spot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the spot using partial update
        Spot partialUpdatedSpot = new Spot();
        partialUpdatedSpot.setId(spot.getId());

        partialUpdatedSpot.spotNo(UPDATED_SPOT_NO).level(UPDATED_LEVEL);

        restSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpot.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpot))
            )
            .andExpect(status().isOk());

        // Validate the Spot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpotUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSpot, spot), getPersistedSpot(spot));
    }

    @Test
    @Transactional
    void fullUpdateSpotWithPatch() throws Exception {
        // Initialize the database
        insertedSpot = spotRepository.saveAndFlush(spot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the spot using partial update
        Spot partialUpdatedSpot = new Spot();
        partialUpdatedSpot.setId(spot.getId());

        partialUpdatedSpot
            .name(UPDATED_NAME)
            .spotNo(UPDATED_SPOT_NO)
            .level(UPDATED_LEVEL)
            .spotRow(UPDATED_SPOT_ROW)
            .spotColumn(UPDATED_SPOT_COLUMN)
            .occupied(UPDATED_OCCUPIED);

        restSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpot.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpot))
            )
            .andExpect(status().isOk());

        // Validate the Spot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpotUpdatableFieldsEquals(partialUpdatedSpot, getPersistedSpot(partialUpdatedSpot));
    }

    @Test
    @Transactional
    void patchNonExistingSpot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spot.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, spot.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(spot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(spot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpotMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(spot)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Spot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpot() throws Exception {
        // Initialize the database
        insertedSpot = spotRepository.saveAndFlush(spot);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the spot
        restSpotMockMvc
            .perform(delete(ENTITY_API_URL_ID, spot.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return spotRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Spot getPersistedSpot(Spot spot) {
        return spotRepository.findById(spot.getId()).orElseThrow();
    }

    protected void assertPersistedSpotToMatchAllProperties(Spot expectedSpot) {
        assertSpotAllPropertiesEquals(expectedSpot, getPersistedSpot(expectedSpot));
    }

    protected void assertPersistedSpotToMatchUpdatableProperties(Spot expectedSpot) {
        assertSpotAllUpdatablePropertiesEquals(expectedSpot, getPersistedSpot(expectedSpot));
    }
}
