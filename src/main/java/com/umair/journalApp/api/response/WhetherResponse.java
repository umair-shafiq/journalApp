package com.umair.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhetherResponse
{
  public Current current;

  @Getter
  @Setter
  public class Current
  {
    @JsonProperty("last_updated")
    public String lastUpdated;

    @JsonProperty("temp_c")
    public double tempC;

    @JsonProperty("temp_f")
    public double tempF;

    @JsonProperty("is_day")
    public int isDay;

    @JsonProperty("wind_kph")
    public double windKph;

    @JsonProperty("wind_dir")
    public String windDir;
    public int humidity;
    public int cloud;

  }

}






