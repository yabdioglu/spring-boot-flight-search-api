package com.yabdioglu.flight.flight;

import com.yabdioglu.flight.flight.dto.FlightDTO;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    Flight save(FlightDTO flight);

    Flight getById(Long id);

    Flight update(final Long id, FlightDTO flight);

    void deleteById(Long id);

    List<Flight> getAll();

    List<Flight> search(Long departureId, Long destinationId, LocalDate departureDate, LocalDate returnDate);

    void saveAll(List<FlightDTO> flightRequests);
}
