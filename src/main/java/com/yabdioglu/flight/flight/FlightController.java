package com.yabdioglu.flight.flight;

import com.yabdioglu.flight.flight.dto.FlightDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yabdioglu.flight.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequestMapping("/api/v1/flights")
@Tag(name = "Flight", description = "Flight API")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Save flight",
            description = "Save flight")
    public ResponseEntity<Flight> save(@RequestBody FlightDTO request) {
        return ResponseEntity.ok(flightService.save(request));
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get flight by id",
            description = "Get flight by id")
    public ResponseEntity<Flight> getById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get all flights",
            description = "Get all flights")
    public ResponseEntity<List<Flight>> getAll() {
        return ResponseEntity.ok(flightService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Update flight",
            description = "Update flight")
    public ResponseEntity<Flight> update(@PathVariable Long id, @RequestBody FlightDTO request) {
        return ResponseEntity.ok(flightService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete flight by id",
            description = "Delete flight by id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        flightService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
