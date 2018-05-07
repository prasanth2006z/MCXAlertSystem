package com.mcx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Hello world!
 */
@SpringBootApplication
public class MCXAlertSystem {
    private static final Logger logger = LogManager.getLogger(MCXAlertSystem.class);

    public static void main(String[] args) {
        logger.debug("MCX Alert System started!!!");
        SpringApplication.run(MCXAlertSystem.class, args);
    }
}
