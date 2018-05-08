package com.mcx;

import com.mcx.service.impl.AESServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Base64;
import java.util.List;

/**
 * Hello world!
 */
@SpringBootApplication
public class MCXAlertSystem{
  //Ma5VFSKeOZyJj9ZsjNQxzg==
  private static final Logger logger = LogManager.getLogger(MCXAlertSystem.class);

  public static byte[] key;

  public static void main(String[] args) throws Exception {
    logger.debug("MCX Alert System started!!!");
    if(args!=null && args[0]!=null){
     //String password="Prasanth@123";


//      byte[] pas=Base64.getEncoder().encode(new AESServiceImpl().encrypt(password));
//      System.out.println("pas==="+pas);
      Config.PASSWORD = new AESServiceImpl().decrypt(Base64.getDecoder().decode(args[0].getBytes()));
    }
    SpringApplication.run(MCXAlertSystem.class, args);
  }
}

//https://console.firebase.google.com/project/mcxnotificationmanager/overview
