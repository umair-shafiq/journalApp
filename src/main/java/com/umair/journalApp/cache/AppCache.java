package com.umair.journalApp.cache;

import com.umair.journalApp.entity.ConfigJournalAppEntity;
import com.umair.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache
{

  public enum keys
  {
    WHEATHER_API;
  }
  @Autowired
  private ConfigJournalAppRepository configJournalAppRepository;
  public Map<String, String> APP_CACHE;

  @PostConstruct
  public void init()
  {
    APP_CACHE = new HashMap<>();
List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
    for (ConfigJournalAppEntity entity : all)
    {
      APP_CACHE.put(entity.getKey(), entity.getValue());
    }
  }
}
