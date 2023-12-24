package com.yabdioglu.flight.search;

import com.yabdioglu.flight.flight.Flight;
import com.yabdioglu.flight.flight.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.yabdioglu.flight.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequestMapping("/api/v1/search")
@Tag(name = "Search", description = "Search API")
public class SearchController {

    private final FlightService flightService;

    public SearchController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Search flights",
            description = "Search flights by departure, destination and departure date. " +
                    "If return date is provided, it will search for round trip flights.")
    public ResponseEntity<List<Flight>> search(
            @RequestParam Long departureId,
            @RequestParam Long destinationId,
            @RequestParam(value = "2023-12-24") LocalDate departureDate,
            @RequestParam(required = false, value = "2023-12-26") LocalDate returnDate
    ) {
        return ResponseEntity.ok(flightService.search(departureId, destinationId, departureDate, returnDate));
    }
}
