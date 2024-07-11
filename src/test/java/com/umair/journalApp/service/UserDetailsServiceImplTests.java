package com.umair.journalApp.service;

import com.umair.journalApp.entity.User;
import com.umair.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests
{
  @InjectMocks
  private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

  @Mock
  private UserRepository userRepository;

  @BeforeEach
  public void setUp()
  {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void loadUserByUsernameTest()
  {
    when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("Umair").password("password").roles(new ArrayList<>()).build());
    UserDetails user = customUserDetailsServiceImpl.loadUserByUsername("Umair");
    Assertions.assertNotNull(user);
  }
}
