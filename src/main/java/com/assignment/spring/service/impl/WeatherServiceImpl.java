package com.assignment.spring.service.impl;

import com.assignment.spring.client.OpenWeatherClient;
import com.assignment.spring.client.model.WeatherResponse;
import com.assignment.spring.config.WeatherConfigProperties;
import com.assignment.spring.domain.WeatherEntity;
import com.assignment.spring.mapper.DataMapper;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Default implementation for WeatherService. It applies OpenWeatherClient and WeatherRepository to
 * handle all weather business.
 */
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository repository;

    private final OpenWeatherClient openWeatherClient;

    private final DataMapper dataMapper;

    private final WeatherConfigProperties weatherConfigs;

    /**
     * Get weather information from OpenWeather and persist the result in the database.
     * @param city the name of the city.
     * @return weather entity
     */
    @Override
    @Transactional
    public WeatherEntity findWeather(String city) {
        WeatherResponse response = openWeatherClient.getWeather(city, weatherConfigs.getId());

        return repository.save(dataMapper.toWeatherEntity(response));
    }
}
