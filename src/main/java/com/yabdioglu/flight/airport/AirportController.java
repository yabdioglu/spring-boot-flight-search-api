package com.yabdioglu.flight.airport;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yabdioglu.flight.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequestMapping("/api/v1/airports")
@Tag(name = "Airport", description = "Airport API")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Save airport",
            description = "Save airport")
    public ResponseEntity<Airport> save(Airport airport) {
        return ResponseEntity.ok(airportService.save(airport));
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get all airports",
            description = "Get all airports")
    public ResponseEntity<List<Airport>> getAll() {
        return ResponseEntity.ok(airportService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get airport by id",
            description = "Get airport by id")
    public ResponseEntity<Airport> getById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getById(id));
    }

    @PutMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Update airport",
            description = "Update airport")
    public ResponseEntity<Airport> update(Airport airport) {
        return ResponseEntity.ok(airportService.update(airport));
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete airport by id",
            description = "Delete airport by id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        airportService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
