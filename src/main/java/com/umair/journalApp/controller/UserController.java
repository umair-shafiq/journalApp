package com.umair.journalApp.controller;

import com.umair.journalApp.entity.User;
import com.umair.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers()
  {
    List<User> allUsers = userService.getAllUsers();
    return new ResponseEntity<>(allUsers, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user)
  {
    try
    {
      User userinDB = userService.getUserByUsername(user.getUsername());
      if (user.getUsername().equals(userinDB.getUsername()))
      {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
      }

      userService.saveUser(user);
      return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    catch (Exception e)
    {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("id/{id}")
  public ResponseEntity<User> getUserById(@PathVariable ObjectId id)
  {
    User user = userService.getUserById(id);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

//  @DeleteMapping("id/{id}")
//  public ResponseEntity<?> deleteUser(@PathVariable ObjectId id)
//  {
//    userService.deleteUser(id);
//    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }

  @PutMapping("/{username}")
  public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String username)
  {
    User userInDB = userService.getUserByUsername(username);
    if (userInDB != null)
    {
      userInDB.setUsername(user.getUsername());
      userInDB.setPassword(user.getPassword());
      userService.saveUser(userInDB);
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}

