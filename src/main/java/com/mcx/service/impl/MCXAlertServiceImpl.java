package com.mcx.service.impl;

import com.mcx.constants.NotificationConstants;
import com.mcx.model.AlertSystem;
import com.mcx.model.Notification;
import com.mcx.model.User;
import com.mcx.service.EmailNotificationServer;
import com.mcx.service.FCMServer;
import com.mcx.service.MCXAlertService;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by prasanth.p on 06/05/18.
 */
public class MCXAlertServiceImpl implements MCXAlertService {

    private static final Logger logger = LogManager.getLogger(MCXAlertServiceImpl.class);

    @Autowired
    AlertSystem alertSystem;

    @Autowired
    FCMServer fcmServer;

    @Autowired
    EmailNotificationServer emailNotificationServer;

    public String registerUser(String userName, String password) {
        logger.debug("User Registration: username " + userName + "password:" + password);
        if (validateUser(userName, password)) {
            int size = (alertSystem.getUserMap().size() + 1);
            alertSystem.getUserMap().put(size, constructUser(userName, password));
            emailNotificationServer.sedEmail(alertSystem.getUserMap());
            return NotificationConstants.SUCCESS;
        }
        return NotificationConstants.REGISTER_USER_ERROR;
    }

    public Map<String, String> login(String userName, String password) {
        logger.debug("User login: username " + userName);
        Map<String, String> map = new HashMap<>();
        if (validateUser(userName, password)) {
            User user = createUUID(userName, password);
            if (user != null) {
                map.put("status", NotificationConstants.SUCCESS);
                map.put("token", user.getAuthorization());
                map.put("priority", user.getRole());
                alertSystem.getLoginMap().put(user.getAuthorization(), null);
            }
        } else {
            logger.debug("User login failed!!!: username " + userName + "password:" + password);
            map.put("status", NotificationConstants.USER_LOGIN_ERROR_STATUS);
            map.put("Error Message", NotificationConstants.USER_LOGIN_ERROR);
        }
        return map;
    }

    public String registerDevice(String authn, String token) {
        if (validateAuthn(authn)) {
            alertSystem.getLoginMap().put(authn, token);
            return NotificationConstants.SUCCESS;
        }
        return NotificationConstants.USER_LOGIN_ERROR_STATUS;
    }


    public String writeNotifications(Notification notification, String authn) {
        String message = validate(notification);
        if (message.equals(NotificationConstants.SUCCESS)) {
            String token = alertSystem.getLoginMap().get(authn);
            message = sendNotifications(notification, token);
            //sendEmail();
        }
        return message;
    }

    public String logout(String authn) {
        if (alertSystem.getLoginMap() != null && alertSystem.getLoginMap().size() > 0 && alertSystem.getLoginMap().containsKey(authn)) {
            alertSystem.getLoginMap().remove(authn);
            return NotificationConstants.USER_LOGOUT_SUCCESS;
        } else {
            return NotificationConstants.USER_LOGOUT_ERROR_STATUS;
        }
    }

    private String sendNotifications(Notification notification, String token) {
        Response response = fcmServer.sendFCMNotification(notification, token);
        logger.debug("Puh response: " + response);
        if (response.isSuccessful()) {
            return NotificationConstants.SUCCESS;
        } else {
            return NotificationConstants.USER_LOGIN_ERROR_STATUS;
        }

    }

    private boolean validateAuthn(String auth) {
        logger.debug("Validate authn:" + auth);
        if (alertSystem.getLoginMap() != null && alertSystem.getLoginMap().size() > 0) {
            logger.debug("Logged-In user size:" + alertSystem.getLoginMap().size());
            if (alertSystem.getLoginMap().containsKey(auth)) {
                return true;
            }
            return false;
        } else {
            logger.debug("No user LoggedIn yet!!!");
            return false;
        }
    }


    private User constructUser(String userName, String password) {
        User user = new User();
        user.setUserId(userName);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(getUserRole(userName, password));
        user.setDate(new Date());
        return user;
    }


    private String getUserRole(String userName, String password) {
        if (userName.equals("test") && password.equals("test")) {
            return "Admin";
        } else {
            return "user";
        }
    }


    private boolean validateUser(String userName, String password) {
        if (userName != null && password != null) {
            return true;
        }
        return false;
    }


    private User getUserByUserNameAndPassword(String userName, String password) {
        logger.debug("Search user by username and password!!!");
        if (userName != null && password != null) {
            if (alertSystem.getUserMap() != null && alertSystem.getUserMap().size() > 0) {
                for (Integer key : alertSystem.getUserMap().keySet()) {
                    User user = alertSystem.getUserMap().get(key);
                    if (user != null) {
                        if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                            logger.debug("User Exist with username as: " + userName);
                            return user;
                        }
                    } else {
                        logger.debug("Invalid User : userName:" + userName + ", password:" + password);
                    }
                }
            }
            return null;
        }
        return null;
    }

    private User getUserByAuthorization(String authn) {
        logger.debug("Search user by authorization!!!");
        if (authn != null) {
            if (alertSystem.getUserMap() != null && alertSystem.getUserMap().size() > 0) {
                for (Integer key : alertSystem.getUserMap().keySet()) {
                    User user = alertSystem.getUserMap().get(key);
                    if (user != null) {
                        if (authn.equals(user.getAuthorization())) {
                            logger.debug("User Exist with username as: " + user.getUserName());
                            return user;
                        }
                    } else {
                        logger.debug("Invalid User authn: " + authn);
                    }
                }
            }
        }
        return null;
    }

    private User createUUID(String userName, String password) {
        User user = getUserByUserNameAndPassword(userName, password);
        if (user != null) {
            String authn = UUID.randomUUID().toString();
            user.setAuthorization(authn);
            logger.debug("Authn generated for user: " + userName);
            return user;
        }
        return null;
    }

    public static String validate(Notification notification) {
        String message = NotificationConstants.SUCCESS;
        if (notification != null) {
            if (StringUtils.isEmpty(notification.getType())) {
                message = NotificationConstants.BUY_SELL_ERROR;
                return message;
            }
            if (StringUtils.isEmpty(notification.getName())) {
                message = NotificationConstants.COMMODITY_ERROR;
                return message;
            }
            if (StringUtils.isEmpty(notification.getBET())) {
                message = NotificationConstants.BET_ERROR;
                return message;
            }
            if (StringUtils.isEmpty(notification.getStopLoss())) {
                message = NotificationConstants.SL_ERROR;
                return message;
            }
            if (StringUtils.isEmpty(notification.getTGT())) {
                message = NotificationConstants.TGT_ERROR;
                return message;
            }
        } else {
            message = NotificationConstants.REQUEST_ERROR;
            return message;
        }
        return message;
    }
}
