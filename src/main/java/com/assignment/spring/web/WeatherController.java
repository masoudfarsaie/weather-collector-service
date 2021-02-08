package com.assignment.spring.web;

import com.assignment.spring.mapper.DataMapper;
import com.assignment.spring.service.WeatherService;
import com.assignment.spring.web.api.WeatherControllerApi;
import com.assignment.spring.web.model.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Default implementation of {@link WeatherControllerApi}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherController implements WeatherControllerApi {

    private final WeatherService weatherService;

    private final DataMapper dataMapper;

    @Override
    public ResponseEntity<WeatherResponseDto> weather(String city) {
        log.debug("Received city id: {}", city);

        return ResponseEntity.ok(dataMapper.toWeatherResponseDto(weatherService.findWeather(city)));
    }
}
