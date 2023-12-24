package com.yabdioglu.flight.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetween(Long departureId, Long destinationId, LocalDateTime departureTimeStart, LocalDateTime departureTimeEnd);

    List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetweenAndReturnDateBetween(Long departureId, Long destinationId, LocalDateTime departureTimeStart, LocalDateTime departureTimeEnd, LocalDateTime returnTimeStart, LocalDateTime returnTimeEnd);
}
