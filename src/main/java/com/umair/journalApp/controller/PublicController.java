package com.umair.journalApp.controller;

import com.umair.journalApp.entity.User;
import com.umair.journalApp.service.CustomUserDetailsServiceImpl;
import com.umair.journalApp.service.RedisService;
import com.umair.journalApp.service.UserService;
import com.umair.journalApp.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController
{
  @Autowired UserService userService;
  @Autowired
  private RedisService redisService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private CustomUserDetailsServiceImpl userDetailsService;
  @Autowired
  private JwtUtil jwtUtil;

  Logger logger = LoggerFactory.getLogger(PublicController.class);

  @PostMapping("/signup")
  public ResponseEntity<User> signup(@RequestBody User user)
  {
    try
    {
      userService.saveNewUser(user);
      return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    catch (Exception e)
    {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody User user)
  {
    try
    {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
      UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
      String jwt = jwtUtil.generateToken(userDetails.getUsername());
      return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
    catch (Exception e)
    {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("/log")
  public String index()
  {
    logger.trace("A TRACE Message");
    logger.debug("A DEBUG Message");
    logger.info("An INFO Message");
    logger.warn("A WARN Message");
    logger.error("An ERROR Message");
    return "Howdy! Check out the Logs to see the output...";
  }

  @PostMapping("/setEmail")
  public void setEmail(@RequestBody String email)
  {
    redisService.setEmail(email);
  }

  @GetMapping("/salary")
  public ResponseEntity<String> getSalary()
  {
    return new ResponseEntity<>(redisService.getName(), HttpStatus.OK);
  }

}
