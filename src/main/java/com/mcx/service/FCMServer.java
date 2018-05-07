package com.mcx.service;

import com.mcx.model.Notification;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by prasanth.p on 06/05/18.
 */
public interface FCMServer {

    String getAccessToken() throws IOException;

    Response sendFCMNotification(Notification notification, String authn);
}
