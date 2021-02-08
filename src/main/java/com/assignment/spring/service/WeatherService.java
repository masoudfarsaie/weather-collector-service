package com.assignment.spring.service;

import com.assignment.spring.domain.WeatherEntity;

public interface WeatherService {

    WeatherEntity findWeather(String city);
}
