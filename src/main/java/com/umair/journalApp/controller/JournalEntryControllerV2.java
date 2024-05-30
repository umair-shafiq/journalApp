package com.umair.journalApp.controller;

import com.umair.journalApp.entity.JournalEntry;
import com.umair.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2
{
  @Autowired
  private JournalEntryService journalEntryService;


  @GetMapping
  public ResponseEntity<?> getAllJournalEntries()
  {
    List<JournalEntry> journalEntries = journalEntryService.getAllEntries();

    if (journalEntries != null && !journalEntries.isEmpty())
    {
      return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry)
  {
    try
    {
      journalEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(journalEntry);
      return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
    }
    catch (Exception e)
    {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("id/{myId}")
  public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId)
  {
    Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);
    if (journalEntry.isPresent())
    {
      return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId)
  {
    journalEntryService.deleteEntryById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("id/{id}")
  public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry)
  {
    JournalEntry journalEntry = journalEntryService.getEntryById(id).orElse(null);
    if (journalEntry != null)
    {
      journalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : journalEntry.getTitle());
      journalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : journalEntry.getContent());
      journalEntryService.saveEntry(journalEntry);
      return new ResponseEntity<>(journalEntry, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }
}
