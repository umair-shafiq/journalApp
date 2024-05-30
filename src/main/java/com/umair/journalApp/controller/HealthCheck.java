package com.umair.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck
{
  @GetMapping("/health-check")
  public String healthCheck()
  {
    return "Journal App is up and running!";
  }
}
