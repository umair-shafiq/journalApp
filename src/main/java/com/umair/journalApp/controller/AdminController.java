package com.umair.journalApp.controller;

import com.umair.journalApp.cache.AppCache;
import com.umair.journalApp.entity.User;
import com.umair.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController
{
  @Autowired
  private UserService userService;

  @Autowired
  private AppCache appCache;

  @GetMapping("/all-users")
  public ResponseEntity<List<User>> getAllUsers()
  {
    List<User> userList = userService.getAll();
    if (userList != null && !userList.isEmpty())
    {
      return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/create-admin-user")
  public void createAdminUser(@RequestBody User user)
  {
    userService.saveUAdmin(user);
  }

  @GetMapping("/clear-app-cache")
  public void clearAppCache()
  {
    appCache.init();
  }
}
