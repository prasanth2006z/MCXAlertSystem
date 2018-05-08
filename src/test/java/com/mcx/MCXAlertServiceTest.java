package com.mcx;

import com.mcx.constants.NotificationConstants;
import com.mcx.model.AlertSystem;
import com.mcx.model.Notification;
import com.mcx.service.FCMServer;
import com.mcx.service.MCXAlertService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by prasanth.p on 06/05/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class MCXAlertServiceTest {

    @Autowired
    MCXAlertService mcxAlertService;

    @Autowired
    AlertSystem alertSystem;

    @Autowired
    FCMServer fcmServer;

    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    @Test
    public void testRegisterUserWhenUserDetailsAreNull() {
        String response = mcxAlertService.registerUser(null, null);
        Assert.assertEquals(NotificationConstants.REGISTER_USER_ERROR, response);
    }

    @Test
    public void testRegisterUserWhenUserPasswordNull() {
        String response = mcxAlertService.registerUser(USERNAME, null);
        Assert.assertEquals(NotificationConstants.REGISTER_USER_ERROR, response);
    }

    @Test
    public void testRegisterUserWhenUserDetailsNotNull() {
        String response = mcxAlertService.registerUser(USERNAME, PASSWORD);
        String response1 = mcxAlertService.registerUser(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.REGISTER_USER_ERROR, response1);
    }

    @Test
    public void testLoginUserWhenUserDetailsAreNotNull() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        Assert.assertNull(alertSystem.getLoginMap().get(response.get("token")));
    }

    @Test
    public void testLoginUserWhenUserDetailsAreNull() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(null, PASSWORD);
        Assert.assertEquals(NotificationConstants.USER_LOGIN_ERROR_STATUS, response.get("status"));
    }

    @Test
    public void testRegisterDeviceWhenUserDetailsAreNotNull() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        String token = response.get("token");
        Assert.assertNotNull(token);
        Assert.assertEquals(NotificationConstants.DEVICE_REGISTRATION_SUCCESS, mcxAlertService.registerDevice(token, "dummyToken123"));
    }

    @Test
    public void testRegisterDeviceWithInvalidToken() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        String token = response.get("token");
        Assert.assertNotNull(token);
        Assert.assertEquals(NotificationConstants.INVALID_AUTHENTICATION, mcxAlertService.registerDevice(token + "ddd", "dummyToken123"));
    }


    @Test
    public void testWriteNotificationsWhereNotificationIsNull() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        String token = response.get("token");
        Assert.assertNotNull(token);
        Assert.assertEquals(NotificationConstants.REQUEST_ERROR, mcxAlertService.writeNotifications(null,token));
    }

    //@Test
    public void testWriteNotificationsWhereNotificationIsNotNull() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        String token = response.get("token");
        mcxAlertService.registerDevice(token,"abcvd");
        Assert.assertNotNull(token);
        Assert.assertEquals(NotificationConstants.USER_LOGIN_ERROR_STATUS, mcxAlertService.writeNotifications(getNotification(),token));
    }

    @Test
    public void testWriteNotificationsWhereNotificationTGTIsNull() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        String token = response.get("token");
        Assert.assertNotNull(token);
        Notification n=getNotification();
        n.setTGT(null);
        Assert.assertEquals(NotificationConstants.TGT_ERROR, mcxAlertService.writeNotifications(n,token));
    }

    @Test
    public void testLogoutWithInvalidAuthn() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        String token = response.get("token");
        Assert.assertNotNull(token);
        Assert.assertEquals(NotificationConstants.USER_LOGOUT_ERROR_STATUS, mcxAlertService.logout(token+"dddd"));
    }

    @Test
    public void testLogoutWithvalidAuthn() {
        mcxAlertService.registerUser(USERNAME, PASSWORD);
        Map<String, String> response = mcxAlertService.login(USERNAME, PASSWORD);
        Assert.assertEquals(NotificationConstants.SUCCESS, response.get("status"));
        String token = response.get("token");
        Assert.assertNotNull(token);
        Assert.assertEquals(NotificationConstants.USER_LOGOUT_SUCCESS, mcxAlertService.logout(token));
    }

    //@Test
    public void testFCMAccessToken() throws Exception{
        fcmServer.getAccessToken();
    }



    private Notification getNotification() {
        Notification notification = new Notification();
        notification.setType("Buy");
        notification.setBET("123-125");
        notification.setName("Lead");
        notification.setPeriod("May");
        notification.setTGT("130-135");
        notification.setStopLoss("120");
        return notification;

    }

}
