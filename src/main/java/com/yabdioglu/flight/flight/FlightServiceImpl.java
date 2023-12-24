package com.yabdioglu.flight.flight;

import com.yabdioglu.flight.airport.AirportService;
import com.yabdioglu.flight.flight.dto.FlightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    private final AirportService airportService;

    @Override
    public Flight save(final FlightDTO flightRequest) {
        if (Objects.equals(flightRequest.getDepartureAirportId(), flightRequest.getArrivalAirportId())) {
            throw new RuntimeException("Departure and arrival airports cannot be the same");
        }
        Flight flight = new Flight();
        mapToEntity(flightRequest, flight);
        return flightRepository.save(flight);
    }

    @Override
    public Flight getById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    @Override
    public Flight update(final Long id, FlightDTO flight) {
        Flight foundFlight = getById(id);
        mapToEntity(flight, foundFlight);
        return flightRepository.save(foundFlight);
    }

    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> search(Long departureId, Long destinationId, LocalDate departureDate, LocalDate returnDate) {
        LocalDateTime departureTimeStart = departureDate.atStartOfDay();
        LocalDateTime departureTimeEnd = departureDate.atTime(23, 59, 59);
        if (Objects.isNull(returnDate)) {
            return flightRepository
                    .findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetween(departureId, destinationId,
                            departureTimeStart, departureTimeEnd);
        }
        LocalDateTime returnTimeStart = returnDate.atStartOfDay();
        LocalDateTime returnTimeEnd = returnDate.atTime(23, 59, 59);
        return flightRepository
                .findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetweenAndReturnDateBetween(departureId, destinationId,
                        departureTimeStart, departureTimeEnd,
                        returnTimeStart, returnTimeEnd);
    }

    @Override
    public void saveAll(List<FlightDTO> flightRequests) {
        flightRequests.forEach(this::save);
    }

    private void mapToEntity(final FlightDTO flightRequest, final Flight flight) {
        flight.setDepartureDate(flightRequest.getDepartureDate());
        flight.setReturnDate(flightRequest.getReturnDate());
        flight.setDepartureAirport(Objects.isNull(flightRequest.getDepartureAirportId()) ? null : airportService.getById(flightRequest.getDepartureAirportId()));
        flight.setArrivalAirport(Objects.isNull(flightRequest.getArrivalAirportId()) ? null : airportService.getById(flightRequest.getArrivalAirportId()));
        flight.setPrice(flightRequest.getPrice());
    }

    private void mapToDto(final Flight flight, final FlightDTO flightRequest) {
        flightRequest.setDepartureDate(flight.getDepartureDate());
        flightRequest.setReturnDate(flight.getReturnDate());
        flightRequest.setDepartureAirportId(Objects.isNull(flight.getDepartureAirport()) ? null : flight.getDepartureAirport().getId());
        flightRequest.setArrivalAirportId(Objects.isNull(flight.getArrivalAirport()) ? null : flight.getArrivalAirport().getId());
        flightRequest.setPrice(flight.getPrice());
    }
}
