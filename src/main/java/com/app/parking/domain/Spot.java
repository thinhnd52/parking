package com.app.parking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Spot.
 */
@Entity
@Table(name = "spot")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Spot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "spot_no")
    private String spotNo;

    @Column(name = "level")
    private Integer level;

    @Column(name = "spot_row")
    private Integer spotRow;

    @Column(name = "spot_column")
    private Integer spotColumn;

    @Column(name = "occupied")
    private Boolean occupied;

    @JsonIgnoreProperties(value = { "spot", "driver" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "spot")
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Spot id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Spot name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotNo() {
        return this.spotNo;
    }

    public Spot spotNo(String spotNo) {
        this.setSpotNo(spotNo);
        return this;
    }

    public void setSpotNo(String spotNo) {
        this.spotNo = spotNo;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Spot level(Integer level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSpotRow() {
        return this.spotRow;
    }

    public Spot spotRow(Integer spotRow) {
        this.setSpotRow(spotRow);
        return this;
    }

    public void setSpotRow(Integer spotRow) {
        this.spotRow = spotRow;
    }

    public Integer getSpotColumn() {
        return this.spotColumn;
    }

    public Spot spotColumn(Integer spotColumn) {
        this.setSpotColumn(spotColumn);
        return this;
    }

    public void setSpotColumn(Integer spotColumn) {
        this.spotColumn = spotColumn;
    }

    public Boolean getOccupied() {
        return this.occupied;
    }

    public Spot occupied(Boolean occupied) {
        this.setOccupied(occupied);
        return this;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        if (this.ticket != null) {
            this.ticket.setSpot(null);
        }
        if (ticket != null) {
            ticket.setSpot(this);
        }
        this.ticket = ticket;
    }

    public Spot ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spot)) {
            return false;
        }
        return getId() != null && getId().equals(((Spot) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Spot{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", spotNo='" + getSpotNo() + "'" +
            ", level=" + getLevel() +
            ", spotRow=" + getSpotRow() +
            ", spotColumn=" + getSpotColumn() +
            ", occupied='" + getOccupied() + "'" +
            "}";
    }
}
