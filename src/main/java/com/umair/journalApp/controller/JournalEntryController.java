//package com.umair.journalApp.controller;
//
//import com.umair.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController
//{
//  private Map<Long, JournalEntry> journalEntryMap = new HashMap<>();
//
//  @GetMapping
//  public List<JournalEntry> getAllJournalEntries()
//  {
//    return new ArrayList<>(journalEntryMap.values());
//  }
//
//  @PostMapping
//  public boolean createEntry(@RequestBody JournalEntry journalEntry)
//  {
//    journalEntryMap.put(journalEntry.getId(), journalEntry);
//    return true;
//  }
//
//  @GetMapping("id/{myId}")
//  public JournalEntry getJournalEntryById(@PathVariable Long myId)
//  {
//    return journalEntryMap.get(myId);
//  }
//
//  @DeleteMapping("id/{myId}")
//  public JournalEntry deleteJournalEntryById(@PathVariable Long myId)
//  {
//    return journalEntryMap.remove(myId);
//  }
//
//  @PutMapping("id/{id}")
//  public JournalEntry updateJournalEntryById(@PathVariable long id, @RequestBody JournalEntry journalEntry)
//  {
//    journalEntryMap.put(id, journalEntry);
//    return journalEntryMap.put(id, journalEntry);
//  }
//}
