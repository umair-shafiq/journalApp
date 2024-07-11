package com.umair.journalApp.controller;

import com.umair.journalApp.api.response.WhetherResponse;
import com.umair.journalApp.entity.User;
import com.umair.journalApp.repository.UserRepositoryImpl;
import com.umair.journalApp.service.UserService;
import com.umair.journalApp.service.WheatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
  @Autowired
  private UserService userService;


  @Autowired
  private WheatherService wheatherService;

  @Autowired UserRepositoryImpl userRepository;


  @DeleteMapping
  public ResponseEntity<?> deleteUser()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userService.deleteUser(authentication.getName());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<User> updateUser(@RequestBody User user)
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User userInDB = userService.getUserByUsername(username);
    userInDB.setUsername(user.getUsername());
    userInDB.setPassword(user.getPassword());
    userService.saveNewUser(userInDB);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> getUser()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    WhetherResponse response = wheatherService.getWheather("sangla hill");
    String greeting = "";
    if (response != null)
    {
      greeting = " Current Temp is " + response.getCurrent().getTempC() + "C" + " and Wind Speed is " + response.getCurrent().getWindKph() + "Km/h";
    }

    return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
  }

  @GetMapping("/get-User-For-SA")
  public ResponseEntity<?> getUserForSA()
  {
    List<User> users = userRepository.getUserForSA();

    return new ResponseEntity<>(users, HttpStatus.OK);
  }
}

