package com.assignment.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "openweather.service")
public class WeatherConfigProperties {

    private String id;

}
