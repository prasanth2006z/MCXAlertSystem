package com.mcx.constants;

import java.util.Date;

/**
 * @Author: pxp167
 * @Date: 5/2/2018
 *
 */
public class NotificationConstants {

  //Messages
  public final static String SUCCESS = "Success!!!";
  public final static String REGISTER_USER_ERROR = "USER ALREADY REGISTERED!!!";

  public final static String USER_LOGIN_ERROR = "Invalid username and password";
  public final static String USER_LOGIN_ERROR_STATUS = "Error";

  public final static String USER_FCM_ERROR = "Error While pushing notification:";

  public final static String DEVICE_REGISTRATION_SUCCESS = "Device Successfully Registered!!!";
  public final static String INVALID_AUTHENTICATION = "Invalid Authntication";

  public final static String USER_LOGOUT_SUCCESS = "Successfully loged-out";
  public final static String USER_LOGOUT_ERROR_STATUS = "Error: User not loged-in yet!!!";
  public final static String BUY_SELL_ERROR = "Please Select type: Buy/Sell";
  public final static String COMMODITY_ERROR = "Please Select commodity Name";
  public final static String BET_ERROR = "BET From - To don't be empty";
  public final static String SL_ERROR = "Please enter SL";
  public final static String TGT_ERROR = "TGT From - To don't be empty";
  public final static String REQUEST_ERROR = "Request body is empty!!!";

  //mail constants
  public final static String HOST = "smtp.gmail.com";
  public final static int PORT = 587;
  public final static String PROTOCOL = "smtp";
  public final static String AUTH = "true";
  public final static String STARTTLS = "true";
  public final static String DEBUGG = "true";

  public final static String USER_NAME = "MCXAlertSystem@gmail.com";
  public final static String PASSWORD = "Prasanth@123";
  public final static String SENT_FROM = "prasanth2006z@gmail.com";
  public final static String SENT_SUBJECT = "MCX Notification Details :" + new Date();

  //Access Control Constants
  public final static String Access_Control_Allow_Credentials = "Access-Control-Allow-Credentials";
  public final static String Access_Control_Allow_Credentials_VALUES = "true";
  public final static String Access_Control_Allow_Methods = "Access-Control-Allow-Methods";
  public final static String Access_Control_Allow_Methods_VALUES = "POST, GET, OPTIONS, DELETE";
  public final static String Access_Control_Max_Age = "Access-Control-Max-Age";
  public final static String Access_Control_Max_Age_VALUE = "3600";
  public final static String Access_Control_Allow_Headers = "Access-Control-Allow-Headers";
  public final static String Access_Control_Allow_Headers_VALUES = "Content-Type, Accept, X-Requested-With, remember-me,Authorization";

}
