package com.yabdioglu.flight.airport;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Airport save(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Airport getById(Long id) {
        return airportRepository.findById(id).orElseThrow(() -> new RuntimeException("Airport not found"));
    }

    @Override
    public Airport getByCity(String city) {
        return airportRepository.findByCity(city).orElseThrow(() -> new RuntimeException("Airport not found"));
    }

    @Override
    public Airport update(Airport airport) {
        Airport foundAirport = getById(airport.getId());
        foundAirport.setCity(airport.getCity());
        return airportRepository.save(foundAirport);
    }

    @Override
    public void deleteById(Long id) {
        airportRepository.deleteById(id);
    }

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }
}
