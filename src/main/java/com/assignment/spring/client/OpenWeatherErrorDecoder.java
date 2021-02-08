package com.assignment.spring.client;


import com.assignment.spring.exception.WeatherNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

/**
 * Error decoder for handling errors from OpenWeather
 */
@Component
public class OpenWeatherErrorDecoder extends ErrorDecoder.Default {

    /**
     * It decodes error responses. It only customized not found exceptions and returns wrapped feign exception for
     * the rest of them. The customization can be expanded to other error codes according to business requirements.
     * @param methodKey the methodKey.
     * @param response the response object
     * @return expected exception.
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 404:
                return new WeatherNotFoundException();
            default:
                return super.decode(methodKey, response);
        }
    }
}
