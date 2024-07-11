package com.umair.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests
{
  @Autowired
  private EmailService emailService;

  @Test
  void testSendMail()
  {
    emailService.sendEmail("ma3315037@gmail.com", "Testing Java mail sender", "This is a test email");
  }
}
