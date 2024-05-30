package com.umair.journalApp.service;

import com.umair.journalApp.entity.JournalEntry;
import com.umair.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService
{
  @Autowired
  private JournalEntryRepository journalEntryRepository;

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

  public void deleteEntryById(ObjectId id)
  {
    journalEntryRepository.deleteById(id);
  }
}
