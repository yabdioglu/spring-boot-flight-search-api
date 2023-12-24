package com.yabdioglu.flight.jobs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yabdioglu.flight.flight.FlightService;
import com.yabdioglu.flight.flight.dto.FlightDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FlightDataFetchJob {

    private final FlightService flightService;

    @Value("${flight.api.url}")
    private String flightApiUrl;

    private static final Logger LOG = LoggerFactory.getLogger(FlightDataFetchJob.class);

    @Scheduled(cron = "1 0 0 * * *")
    public void fetchFlightData() {
        LOG.info("Fetching flight data");

        HttpResponse httpResponse = execute(flightApiUrl);

        if (Objects.isNull(httpResponse) || Objects.isNull(httpResponse.getStatusLine()) || httpResponse.getStatusLine().getStatusCode() != 200) {
            LOG.error("Flight data could not be fetched");
            return;
        }

        String jsonString = null;
        try {
            jsonString = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            FlightDTO[] flightRequestsArray = getObjectMapper().readValue(jsonString, FlightDTO[].class);
            flightService.saveAll(Arrays.asList(flightRequestsArray));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LOG.info("Flight data fetched successfully");
    }



    protected ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    private HttpResponse execute(String apiUrl) {
        HttpGet httpGet = new HttpGet(apiUrl);
        httpGet.addHeader("content-type", "application/x-www-form-urlencoded");

        int timeout = 60;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();

        try {
            return HttpClientBuilder.create()
                    .setDefaultRequestConfig(config)
                    .build()
                    .execute(httpGet);
        } catch (Exception e) {
            LOG.error("Error occurred while executing http request", e);
        }
        return null;
    }

}
