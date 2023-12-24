package com.yabdioglu.flight.flight.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FlightDTO {

    private Long id;
    private Long departureAirportId;
    private Long arrivalAirportId;

    private LocalDateTime departureDate;

    private LocalDateTime returnDate;
    private BigDecimal price;
}
