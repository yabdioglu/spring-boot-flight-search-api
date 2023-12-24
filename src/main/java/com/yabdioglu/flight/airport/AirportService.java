package com.yabdioglu.flight.airport;

import java.util.List;

public interface AirportService {

    Airport save(Airport airport);

    Airport getById(Long id);

    Airport getByCity(String city);

    Airport update(Airport airport);

    void deleteById(Long id);

    List<Airport> getAll();
}
