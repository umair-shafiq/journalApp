package com.umair.journalApp.service;

import com.umair.journalApp.entity.User;
import com.umair.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserService
{
  @Autowired
  private UserRepository userRepository;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public void saveNewUser(User user)
  {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList("USER"));
    user.setJournalEntries(new ArrayList<>());
    userRepository.save(user);
  }

  public void saveUAdmin(User user)
  {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList("USER", "ADMIN"));
    userRepository.save(user);
  }

  public void saveUser(User user)
  {
    userRepository.save(user);
  }


  public void deleteUser(String username)
  {
    userRepository.deleteByUsername(username);
  }

  public User getUserByUsername(String username)
  {
    return userRepository.findByUsername(username);
  }

  public List<User> getAll()
  {
    return userRepository.findAll();
  }

}
