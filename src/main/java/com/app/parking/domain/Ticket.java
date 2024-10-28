package com.app.parking.domain;

import com.app.parking.domain.enumeration.CarBrand;
import com.app.parking.domain.enumeration.Color;
import com.app.parking.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ticket_no", nullable = false)
    private String ticketNo;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "room")
    private String room;

    @Column(name = "arrival_date")
    private Instant arrivalDate;

    @Column(name = "departure_date")
    private Instant departureDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private CarBrand model;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    @Column(name = "license_plate")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "damanged")
    private Boolean damanged;

    @Column(name = "note")
    private String note;

    @JsonIgnoreProperties(value = { "ticket" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Spot spot;

    @JsonIgnoreProperties(value = { "ticket" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ticket id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNo() {
        return this.ticketNo;
    }

    public Ticket ticketNo(String ticketNo) {
        this.setTicketNo(ticketNo);
        return this;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Ticket lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Ticket firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return this.phone;
    }

    public Ticket phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return this.room;
    }

    public Ticket room(String room) {
        this.setRoom(room);
        return this;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Instant getArrivalDate() {
        return this.arrivalDate;
    }

    public Ticket arrivalDate(Instant arrivalDate) {
        this.setArrivalDate(arrivalDate);
        return this;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getDepartureDate() {
        return this.departureDate;
    }

    public Ticket departureDate(Instant departureDate) {
        this.setDepartureDate(departureDate);
        return this;
    }

    public void setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
    }

    public CarBrand getModel() {
        return this.model;
    }

    public Ticket model(CarBrand model) {
        this.setModel(model);
        return this;
    }

    public void setModel(CarBrand model) {
        this.model = model;
    }

    public Color getColor() {
        return this.color;
    }

    public Ticket color(Color color) {
        this.setColor(color);
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public Ticket licensePlate(String licensePlate) {
        this.setLicensePlate(licensePlate);
        return this;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Status getStatus() {
        return this.status;
    }

    public Ticket status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getDamanged() {
        return this.damanged;
    }

    public Ticket damanged(Boolean damanged) {
        this.setDamanged(damanged);
        return this;
    }

    public void setDamanged(Boolean damanged) {
        this.damanged = damanged;
    }

    public String getNote() {
        return this.note;
    }

    public Ticket note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Spot getSpot() {
        return this.spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Ticket spot(Spot spot) {
        this.setSpot(spot);
        return this;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Ticket driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return getId() != null && getId().equals(((Ticket) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", ticketNo='" + getTicketNo() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", room='" + getRoom() + "'" +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", departureDate='" + getDepartureDate() + "'" +
            ", model='" + getModel() + "'" +
            ", color='" + getColor() + "'" +
            ", licensePlate='" + getLicensePlate() + "'" +
            ", status='" + getStatus() + "'" +
            ", damanged='" + getDamanged() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
