package com.assignment.spring.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Weather entity to persist weather data summary.
 */
@Getter
@Setter
@Entity
@Table(name = "weather")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;

    private String country;

    private Double temperature;

}
