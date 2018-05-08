package com.mcx.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mcx.model.AlertSystem;
import com.mcx.model.Notification;
import com.mcx.service.FCMServer;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by prasanth.p on 06/05/18.
 */
public class FCMServerImpl implements FCMServer {

    private static final Logger logger = LogManager.getLogger(FCMServerImpl.class);
    private static String SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static String FCM_ENDPOINT =
            "https://fcm.googleapis.com/v1/projects/MCXNotificationManager/messages:send";

    @Autowired
    AlertSystem alertSystem;

    private static final Gson gson = new Gson();

    public Response sendFCMNotification(Notification notification, String token) {

        final MediaType mediaType = MediaType.parse("application/json");

        OkHttpClient httpClient = new OkHttpClient();
        Response response = null;
        try {
            Request request = new Request.Builder().url(FCM_ENDPOINT)
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .addHeader("Authorization", "Bearer " + getAccessToken())
                    .post(RequestBody.create(mediaType, buildPayload(notification, token))).build();
            logger.debug("push notification requst:"+gson.toJson(request));

             response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.debug("Message has been sent to FCM server "
                        + response.body().string());
            }

        } catch (IOException e) {
            logger.debug("Error in sending message to FCM server " + e);
        }
        return response;
    }

    public String getAccessToken() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream("mcxnotificationmanager-firebase-adminsdk-tm0o0-90014b8bba.json"))
                .createScoped(Arrays.asList(SCOPE));
        googleCredential.refreshToken();
        String token = googleCredential.getAccessToken();
        return token;
    }


    private String buildPayload(Notification notification, String token) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("token", token);

        JsonObject notificationInfo = new JsonObject();
        notificationInfo.addProperty("type", notification.getType());
        jsonObj.add("data", notificationInfo);

        JsonObject msgObj = new JsonObject();
        msgObj.add("message", jsonObj);

        return msgObj.toString();
    }


}
