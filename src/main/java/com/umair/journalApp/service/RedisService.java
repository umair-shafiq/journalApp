package com.umair.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService
{

  private static final String EMAIL_KEY = "email";

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public void setEmail(String email)
  {
    redisTemplate.opsForValue().set(EMAIL_KEY, email);
  }

  public String getName()
  {
    return redisTemplate.opsForValue().get("name");
  }

  public String getSalary()
  {
    return redisTemplate.opsForValue().get("salary");
  }

  public <T> T get(String key, Class<T> entityClass)
  {
    try
    {
      Object o = redisTemplate.opsForValue().get(key);
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(o.toString(), entityClass);
    }
    catch (Exception e)
    {
      log.error("Error in getting data from redis", e);
      return null;
    }

  }

  public void set(String key, Object o, Long ttl)
  {
    try
    {
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonValue = objectMapper.writeValueAsString(o);
       redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
    }
    catch (Exception e)
    {
      log.error("Error in getting data from redis", e);
    }

  }

}