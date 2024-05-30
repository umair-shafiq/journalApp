package com.umair.journalApp.service;

import com.umair.journalApp.entity.User;
import com.umair.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService
{
  @Autowired
  private UserRepository userRepository;

  public void saveUser(User user)
  {
    userRepository.save(user);
  }

  public User getUserById(ObjectId id)
  {
    return userRepository.findById(id).orElse(null);
  }

  public List<User> getAllUsers()
  {
    return userRepository.findAll();
  }

  public void deleteUser(ObjectId id)
  {
    userRepository.deleteById(id);
  }

  public User getUserByUsername(String username)
  {
    return userRepository.findByUsername(username);
  }
}
