package com.assignment.spring.client;

import com.assignment.spring.client.model.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OpenWeather Client interface in order to communicate with OpenWeather services.
 */
@FeignClient(value = "openweather", url = "${openweather.service.url}")
public interface OpenWeatherClient {

    /**
     * Retrieve current weather data from OpenWeather.
     * @param city the name of the city.
     * @param appId the api key from OpenWeather.
     * @return weather response
     */
    @RequestMapping(method = RequestMethod.GET, value = "/data/2.5/weather", produces = "application/json")
    WeatherResponse getWeather(@RequestParam("q") String city, @RequestParam("APPID") String appId);
}
