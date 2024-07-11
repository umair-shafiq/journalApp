package com.umair.journalApp.controller;

import com.umair.journalApp.entity.JournalEntry;
import com.umair.journalApp.entity.User;
import com.umair.journalApp.service.JournalEntryService;
import com.umair.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2
{
  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserService userService;


  @GetMapping
  public ResponseEntity<?> getAllJournalEntriesOfUser()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User userByUsername = userService.getUserByUsername(authentication.getName());
    List<JournalEntry> journalEntries = userByUsername.getJournalEntries();

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
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      journalEntryService.saveEntry(journalEntry, authentication.getName());
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.getUserByUsername(authentication.getName());
    List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
    if (!collect.isEmpty())
    {
      Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);
      if (journalEntry.isPresent())
      {
        return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId)
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean removed = journalEntryService.deleteEntryById(myId, authentication.getName());
    if (removed)
    {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("id/{id}")
  public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry)
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.getUserByUsername(authentication.getName());
    List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
    if (!collect.isEmpty())
    {
      Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
      if (journalEntry.isPresent())
      {
        JournalEntry old = journalEntry.get();
        old.setTitle(!newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        journalEntryService.saveEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }
}
