package com.mcx.service;

import com.mcx.model.Notification;

import java.util.Map;

/**
 * Created by prasanth.p on 06/05/18.
 */
public interface MCXAlertService {
    /**
     * @param userName
     * @param password
     * @return
     */
    String registerUser(String userName, String password);


    /**
     * @param userName
     * @param password
     * @return
     */
    Map<String, String> login(String userName, String password);


    /**
     * @param authn
     * @param token
     * @return
     */
    public String registerDevice(String authn, String token);

    /**
     * @param notification
     * @return
     */
    String writeNotifications(Notification notification, String authn);

    /**
     * @param authn
     * @return
     */
    String logout(String authn);

}
