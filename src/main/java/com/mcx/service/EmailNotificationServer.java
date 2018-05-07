package com.mcx.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by prasanth.p on 07/05/18.
 */
public interface EmailNotificationServer {

    Future<Boolean> sedEmail(Object obj);
}
