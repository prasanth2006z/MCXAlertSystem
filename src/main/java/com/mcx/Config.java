package com.mcx;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcx.constants.NotificationConstants;
import com.mcx.model.AlertSystem;
import com.mcx.service.EmailNotificationServer;
import com.mcx.service.FCMServer;
import com.mcx.service.MCXAlertService;
import com.mcx.service.impl.EmailNotificationServerImpl;
import com.mcx.service.impl.FCMServerImpl;
import com.mcx.service.impl.MCXAlertServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by prasanth.p on 30/04/18.
 */
@EnableAutoConfiguration
@Configuration
@EnableAsync
@EnableAspectJAutoProxy
public class Config {

  public static String PASSWORD;
  @Bean
  public MappingJackson2HttpMessageConverter jsonMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    jsonConverter.setObjectMapper(objectMapper);
    return jsonConverter;
  }

  @Bean
  public MCXAlertService mcxAlertService() {
    return new MCXAlertServiceImpl();
  }

  @Bean
  public JavaMailSender emailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(NotificationConstants.HOST);
    mailSender.setPort(NotificationConstants.PORT);
    mailSender.setUsername(NotificationConstants.USER_NAME);
    mailSender.setPassword(NotificationConstants.PASSWORD);
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", NotificationConstants.PROTOCOL);
    props.put("mail.smtp.auth", NotificationConstants.AUTH);
    props.put("mail.smtp.starttls.enable", NotificationConstants.STARTTLS);
    props.put("mail.debug", NotificationConstants.DEBUGG);
    return mailSender;
  }

  @Bean
  public AlertSystem alertSystem() {
    return new AlertSystem();
  }
  @Bean
  public FCMServer fcmServer() {
    return new FCMServerImpl();
  }

  @Bean
  public EmailNotificationServer emailNotificationServer() {
    return new EmailNotificationServerImpl();
  }

  @Bean(name = "processExecutor")
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(20);
    executor.setThreadNamePrefix("AsyncConfigurerSupportDemo");
    executor.initialize();
    return executor;
  }
}
