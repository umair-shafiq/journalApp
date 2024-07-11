package com.umair.journalApp.scheduler;

import com.umair.journalApp.cache.AppCache;
import com.umair.journalApp.entity.JournalEntry;
import com.umair.journalApp.entity.User;
import com.umair.journalApp.enums.Sentiment;
import com.umair.journalApp.model.SentimentData;
import com.umair.journalApp.repository.UserRepositoryImpl;
import com.umair.journalApp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserScheduler
{
  @Autowired
  private UserRepositoryImpl userRepository;


  @Autowired
  private EmailService emailService;

  @Autowired
  private AppCache appCache;

  @Autowired
  private KafkaTemplate<String, SentimentData> kafkaTemplate;

  //@Scheduled(cron = "0 0 9 * * SUN")
  public void fetchUserAndSendSaMail()
  {
    List<User> users = userRepository.getUserForSA();
    for (User user : users)
    {
      List<JournalEntry> journalEntries = user.getJournalEntries();
      List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
      Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
      for (Sentiment sentiment : sentiments)
      {
        if (sentiment != null)
        {
          sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
        }
      }

      Sentiment mostFrequentSentiemnt = null;
      int maxCount = 0;
      for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet())
      {
        if (entry.getValue() > maxCount)
        {
          maxCount = entry.getValue();
          mostFrequentSentiemnt = entry.getKey();
        }
      }
      if (mostFrequentSentiemnt != null)
      {
        SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Your most frequent sentiment is: " + mostFrequentSentiemnt).build();
        kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
        // emailService.sendEmail(user.getEmail(), "Most frequent sentiment for last 7 days.", "Your most frequent sentiment is: " + mostFrequentSentiemnt);
        log.info("Sent sentiment data to Kafka: {}", sentimentData);
      }

    }


  }

  @Scheduled(cron = "0 0/10 * ? * *")
  public void fetchWheatherApi()
  {
    appCache.init();
  }
}
