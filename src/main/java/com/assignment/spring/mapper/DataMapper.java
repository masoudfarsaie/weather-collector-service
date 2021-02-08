package com.assignment.spring.mapper;


import com.assignment.spring.client.model.WeatherResponse;
import com.assignment.spring.domain.WeatherEntity;
import com.assignment.spring.web.model.WeatherResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataMapper {

    WeatherResponseDto toWeatherResponseDto(WeatherEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", source = "name")
    @Mapping(target = "country", source = "sys.country")
    @Mapping(target = "temperature", source = "main.temp")
    WeatherEntity toWeatherEntity(WeatherResponse response);

}
