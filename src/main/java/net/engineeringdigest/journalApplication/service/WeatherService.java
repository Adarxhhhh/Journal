package net.engineeringdigest.journalApplication.service;

import net.engineeringdigest.journalApplication.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String apikey = "15b3d89c881a62416df8868fa9a437b8";

    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalAPI = API.replace("CITY", city).replace("API_KEY", apikey);

        //The WeatherResponse.class -> hit the finalAPI with the GET call and deserialize the response into the WeatherResponse POJO
        //Deserializing is the process of converting JSON to POJO
        //Similarly Serialization is the process of converting POJO to JSON or XML
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
