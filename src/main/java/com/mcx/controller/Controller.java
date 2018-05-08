package com.mcx.controller;

import com.google.gson.Gson;
import com.mcx.constants.NotificationConstants;
import com.mcx.model.Notification;
import com.mcx.model.User;
import com.mcx.service.MCXAlertService;
import com.mcx.service.impl.MCXAlertServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: pxp167
 * @Date: 5/7/2018
 *
 */
@EnableAutoConfiguration
@RestController
public class Controller {
  private static final Gson gson = new Gson();
  private static final Logger logger = LogManager.getLogger(MCXAlertServiceImpl.class);

  @Autowired
  MCXAlertService mcxAlertService;

  /**
   * Register User
   * @param user
   * @return
   */
  @RequestMapping(value = "/register/user", method = RequestMethod.POST)
  public ResponseEntity<String> registerUser(@RequestBody User user) {
    logger.debug("Register User from controller: username="+user.getUserName()+", password:"+user.getPassword());
    String response = mcxAlertService.registerUser(user.getUserName(), user.getPassword());
    if (response != null && response.equals(NotificationConstants.SUCCESS)) {
      logger.debug("User registered successfully!!!");
      return new ResponseEntity(gson.toJson(response), HttpStatus.ACCEPTED);
    } else {
      logger.debug("User registeration exception");
      return new ResponseEntity(gson.toJson(response), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Login
   * @param user
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<Map<String, String>> login(@RequestBody User user){
    logger.debug("login User from controller: username="+user.getUserName()+", password:---");
    Map<String, String> map=mcxAlertService.login(user.getUserName(),user.getPassword());
    if(map!=null && map.size()>0){
      if(map.get("status")!=null && map.get("status").equals(NotificationConstants.SUCCESS)){
        logger.debug("Successful login. Details: "+map);
        return new ResponseEntity(gson.toJson(map), HttpStatus.ACCEPTED);
      }else{
        logger.debug("Login failed. Details:"+map);
        return new ResponseEntity(gson.toJson(map), HttpStatus.BAD_REQUEST);
      }
    }
    logger.debug("Login failed. Details:"+map);
    return new ResponseEntity(gson.toJson(map), HttpStatus.BAD_REQUEST);
  }

  /**
   * Register device
   * @param authorization
   * @param token
   * @return
   */
  @RequestMapping(value = "/register/device", method = RequestMethod.POST)
  public ResponseEntity<String> registerDevice(@RequestHeader("Authorization") String authorization,@RequestBody  String token){
    logger.debug("login User from controller: authorization="+authorization+", token:"+token);
    String response= mcxAlertService.registerDevice(authorization,token);
    if(response!=null && response.equals(NotificationConstants.DEVICE_REGISTRATION_SUCCESS)){
      logger.debug("Device registered successfully!!!"+response);
      return new ResponseEntity(gson.toJson(response), HttpStatus.ACCEPTED);
    }
    logger.debug("Device registeration Error:"+response);
    return new ResponseEntity(gson.toJson(response), HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/push", method = RequestMethod.POST)
  public ResponseEntity<String> pushNotifications(@RequestHeader("Authorization") String authorization,@RequestBody Notification notification){
    String response=mcxAlertService.writeNotifications(notification,authorization);
    if(response!=null && response.equals(NotificationConstants.SUCCESS)){
      logger.debug("Notification pushed successfully: "+response);
      return new ResponseEntity(gson.toJson(response), HttpStatus.ACCEPTED);
    }else{
      logger.debug("Push notification Error:"+response);
      return new ResponseEntity(gson.toJson(response), HttpStatus.BAD_REQUEST);
    }
  }
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  public ResponseEntity<String>  logout(@RequestHeader("Authorization") String authorization){
    String response=mcxAlertService.logout(authorization);
    if(response!=null && response.equals(NotificationConstants.SUCCESS)){
      logger.debug("User logged out successfully: "+response);
      return new ResponseEntity(gson.toJson(response), HttpStatus.ACCEPTED);
    }else{
      logger.debug("Error while log out:"+response);
      return new ResponseEntity(gson.toJson(response), HttpStatus.BAD_REQUEST);
    }
  }
}
