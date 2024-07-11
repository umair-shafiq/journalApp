package com.umair.journalApp.service;

import com.umair.journalApp.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentimentConsumerService
{
  @Autowired
  private EmailService emailService;

  @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
  private void consume(SentimentData sentimentData)
  {
    log.info("Received sentiment data: {}", sentimentData);
    sendEmail(sentimentData);
  }

  public void sendEmail(SentimentData sentimentData)
  {
    log.info("Sending email to: {}", sentimentData.getEmail());
    try
    {
      emailService.sendEmail(sentimentData.getEmail(), "Most frequent sentiment for last 7 days. kafka", sentimentData.getSentiment());
      log.info("Email sent successfully to: {}", sentimentData.getEmail());
    }
    catch (Exception e)
    {
      log.error("Failed to send email to: {}", sentimentData.getEmail(), e);
    }
  }
}
