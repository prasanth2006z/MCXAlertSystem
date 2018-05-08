package com.mcx;

import com.mcx.service.EmailNotificationServer;
import com.mcx.service.impl.EmailNotificationServerImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Future;

/**
 * @Author: pxp167
 * @Date: 5/7/2018
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class EmailNotificationServerTest {
  @Autowired
  JavaMailSender emailSender;

  @Autowired
  EmailNotificationServer emailNotificationServer;

  @Test
  public void doSendEmailWherePayloadIsNUll() {
    Assert.assertNotNull(emailNotificationServer.sedEmail(null));
  }

  @Test
  public void doSendEmailWherePayloadIsNotNUll() {
    Assert.assertNotNull(emailNotificationServer.sedEmail("Test"));
  }
}
