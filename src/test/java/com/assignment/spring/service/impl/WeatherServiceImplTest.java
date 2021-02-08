package com.assignment.spring.service.impl;

import com.assignment.spring.client.OpenWeatherClient;
import com.assignment.spring.client.model.Main;
import com.assignment.spring.client.model.Sys;
import com.assignment.spring.client.model.WeatherResponse;
import com.assignment.spring.config.WeatherConfigProperties;
import com.assignment.spring.domain.WeatherEntity;
import com.assignment.spring.mapper.DataMapper;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.service.WeatherService;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= WeatherServiceImplTest.Config.class)
class WeatherServiceImplTest {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private DataMapper dataMapper;

    @MockBean
    private WeatherRepository repository;

    @MockBean
    private OpenWeatherClient openWeatherClient;

    @MockBean
    private WeatherConfigProperties weatherConfigs;

    @Test
    void findWeather_withValidData_returnsResponse() {
        doReturn(buildResponse()).when(openWeatherClient).getWeather(any(), any());
        doReturn("someKey").when(weatherConfigs).getId();
        doAnswer(inv -> inv.getArguments()[0]).when(repository).save(any());

        WeatherEntity entity = weatherService.findWeather("Test");

        assertNotNull(entity);
        assertEquals("Test", entity.getCity());
        assertEquals("FR", entity.getCountry());
        assertEquals(458.2, entity.getTemperature());
    }

    @Test
    void findWeather_withServiceUnavailable_returnsException() {
        doThrow(FeignException.class).when(openWeatherClient).getWeather(any(), any());
        doReturn("someKey").when(weatherConfigs).getId();
        doAnswer(inv -> inv.getArguments()[0]).when(repository).save(any());

        assertThrows(FeignException.class, () -> weatherService.findWeather("Test"));

        verify(repository, times(0)).save(any());
    }

    private WeatherResponse buildResponse() {
        WeatherResponse response = new WeatherResponse();

        response.setId(0);
        response.setName("Test");
        response.setSys(new Sys());
        response.setMain(new Main());

        response.getSys().setCountry("FR");
        response.getMain().setTemp(458.2);

        return response;
    }

    @ComponentScan(basePackages = {"com.assignment.spring.service","com.assignment.spring.mapper"})
    static class Config {

    }
}