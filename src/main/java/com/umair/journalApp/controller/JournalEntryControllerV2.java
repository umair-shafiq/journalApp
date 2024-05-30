package com.umair.journalApp.controller;

import com.umair.journalApp.entity.JournalEntry;
import com.umair.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2
{
  @Autowired
  private JournalEntryService journalEntryService;


  @GetMapping
  public List<JournalEntry> getAllJournalEntries()
  {
    return journalEntryService.getAllEntries();
  }

  @PostMapping
  public JournalEntry createEntry(@RequestBody JournalEntry journalEntry)
  {
    journalEntry.setDate(LocalDateTime.now());
    journalEntryService.saveEntry(journalEntry);
    return journalEntry;
  }

  @GetMapping("id/{myId}")
  public JournalEntry getJournalEntryById(@PathVariable ObjectId myId)
  {
    return journalEntryService.getEntryById(myId);
  }

  @DeleteMapping("id/{myId}")
  public boolean deleteJournalEntryById(@PathVariable ObjectId myId)
  {
    journalEntryService.deleteEntryById(myId);
    return true;
  }

  @PutMapping("id/{id}")
  public JournalEntry updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry)
  {
    JournalEntry journalEntry = journalEntryService.getEntryById(id);
    if (journalEntry != null)
    {
      journalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ?  newEntry.getTitle() : journalEntry.getTitle());
      journalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ?  newEntry.getContent() : journalEntry.getContent());
    }
    journalEntryService.saveEntry(journalEntry);
    return journalEntry;
  }
}
