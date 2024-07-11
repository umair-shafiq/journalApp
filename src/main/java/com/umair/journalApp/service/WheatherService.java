package com.umair.journalApp.service;

import com.umair.journalApp.api.response.WhetherResponse;
import com.umair.journalApp.cache.AppCache;
import com.umair.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WheatherService
{

  @Value("${wheather.api.key}")
  private String apiKey;

  //private static final String API = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=no";

  @Autowired
   private RestTemplate restTemplate;

  @Autowired
  private AppCache appCache;

  @Autowired
  private RedisService redisService;

  public WhetherResponse getWheather(String city)
  {
    String cityKey = city.replace(" ", "_");
   // String apiUrl = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city + "&aqi=no";
   WhetherResponse whetherResponse = redisService.get("weather_of_" + cityKey, WhetherResponse.class);
   if (whetherResponse != null)
   {
     return whetherResponse;
   }else {
     String apiUrl = appCache.APP_CACHE.get(AppCache.keys.WHEATHER_API.toString()).replace(PlaceHolders.API_KEY, apiKey).replace(PlaceHolders.CITY, city);
     ResponseEntity<WhetherResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, WhetherResponse.class);
     WhetherResponse body = response.getBody();
     if (body != null)
     {
       redisService.set("weather_of_" + cityKey, body, 300L);
     }
     return body;
   }

  }
}
