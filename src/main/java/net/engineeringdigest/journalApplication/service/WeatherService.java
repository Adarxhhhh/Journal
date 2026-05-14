package net.engineeringdigest.journalApplication.service;

import net.engineeringdigest.journalApplication.api.response.WeatherResponse;
import net.engineeringdigest.journalApplication.cache.AppCache;
import net.engineeringdigest.journalApplication.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;
    @Autowired
    private Constants constants;

    public WeatherResponse getWeather(String city){
        String finalAPI = appCache.find(constants.getWeatherApi()).replace(constants.getCity(), city).replace(constants.getWeatherApiKey(), appCache.find("weather_api_key"));

        //The WeatherResponse.class -> hit the finalAPI with the GET call and deserialize the response into the WeatherResponse POJO
        //Deserializing is the process of converting JSON to POJO
        //Similarly Serialization is the process of converting POJO to JSON or XML
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
