package com.assignment.spring.web;

import com.assignment.spring.domain.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.web.model.WeatherResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(stubs="classpath:/mappings")
class WeatherControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    void weather_withValidData_returnsSuccess() {
        String uri = "http://localhost:" + port + "/weather?city={city}";
        ResponseEntity<WeatherResponseDto> response = restTemplate.getForEntity(uri, WeatherResponseDto.class, "London");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("London", response.getBody().getCity());
        assertEquals("GB", response.getBody().getCountry());
        assertEquals(272.96, response.getBody().getTemperature());

        WeatherEntity entity = weatherRepository.findById(response.getBody().getId()).orElse(null);

        assertNotNull(entity);
        assertEquals("London", entity.getCity());
        assertEquals("GB", entity.getCountry());
        assertEquals(272.96, entity.getTemperature());
    }

    @Test
    void weather_withUnknownData_returnsNotFound() {
        String uri = "http://localhost:" + port + "/weather?city={city}";
        ResponseEntity<WeatherResponseDto> response = restTemplate.getForEntity(uri, WeatherResponseDto.class, "notFound");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}