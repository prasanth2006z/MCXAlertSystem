package com.mcx.service.impl;

import com.google.gson.Gson;
import com.mcx.constants.NotificationConstants;
import com.mcx.service.EmailNotificationServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by prasanth.p on 07/05/18.
 */
public class EmailNotificationServerImpl implements EmailNotificationServer {

    private static final Logger logger = LogManager.getLogger(EmailNotificationServerImpl.class);

    private static final Gson gson = new Gson();
    @Autowired
    JavaMailSender emailSender;

    @Async("processExecutor")
    public Future<Boolean> sedEmail(Object obj){
        logger.debug("###Start Processing with Thread id: " + Thread.currentThread().getId());
        if (obj != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(NotificationConstants.SENT_FROM);
            message.setSubject(NotificationConstants.SENT_SUBJECT);
            message.setText(gson.toJson(obj));
            emailSender.send(message);
            return new AsyncResult<>(Boolean.TRUE);
        }
        return new AsyncResult<>(Boolean.FALSE);
    }
}
