package com.yabdioglu.flight.flight;

import com.yabdioglu.flight.airport.Airport;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private BigDecimal price;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date lastUpdated;
}
