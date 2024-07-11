package com.umair.journalApp.service;

import com.umair.journalApp.entity.JournalEntry;
import com.umair.journalApp.entity.User;
import com.umair.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService
{
  @Autowired
  private JournalEntryRepository journalEntryRepository;

  @Autowired
  private UserService userService;

  private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

  @Transactional
  public void saveEntry(JournalEntry journalEntry, String username)
  {
    try
    {
      User userByUsername = userService.getUserByUsername(username);
      journalEntry.setDate(LocalDateTime.now());
      JournalEntry saved = journalEntryRepository.save(journalEntry);
      userByUsername.getJournalEntries().add(saved);
      userService.saveUser(userByUsername);
    }
    catch (Exception e)
    {
      logger.info("Hahahahhahahahah");
      throw new RuntimeException("Error saving journal entry");
    }
  }

  public void saveEntry(JournalEntry journalEntry)
  {
    journalEntryRepository.save(journalEntry);
  }

  public List<JournalEntry> getAllEntries()
  {
    return journalEntryRepository.findAll();
  }

  public Optional<JournalEntry> getEntryById(ObjectId id)
  {
    return journalEntryRepository.findById(id);
  }

  @Transactional
  public boolean deleteEntryById(ObjectId id, String username)
  {
    boolean removed = false;
    try
    {
      User userByUsername = userService.getUserByUsername(username);
      removed = userByUsername.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
      if (removed)
      {
        userService.saveUser(userByUsername);
        journalEntryRepository.deleteById(id);
      }
    }
    catch (Exception e)
    {
      System.out.println(e);
      throw new RuntimeException("An error occurred while deleting the entry.", e);
    }

    return removed;
  }
}
