package com.assignment.spring.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponseDto {
    private Integer id;

    private String city;

    private String country;

    private Double temperature;
}
