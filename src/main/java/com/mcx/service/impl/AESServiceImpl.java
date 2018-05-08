package com.mcx.service.impl;
import com.mcx.constants.NotificationConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * @Author: pxp167
 * @Date: 5/7/2018
 *
 */
public class AESServiceImpl {

  private IvParameterSpec ivParameterSpec;
  private SecretKeySpec secretKeySpec;
  private Cipher cipher;
  private static final String UTF_FORMAT = "UTF-8";
  private static final String AES_PADDING = "AES/CBC/PKCS5PADDING";

  private static String algoritham = "AES";
  private static String secretKey1 = "AdOp8cDev56^%xKu";
  //private static String secretKey256 = "LKg543DNP+)LKg543DNP+<swe!xdCGv*";
  private static String secretKey256 = "AdOp8cDev56^%xKu";

  public AESServiceImpl() throws Exception {
    ivParameterSpec = new IvParameterSpec(secretKey1.getBytes(UTF_FORMAT));
    secretKeySpec = new SecretKeySpec(secretKey256.getBytes(UTF_FORMAT), algoritham);
    cipher = Cipher.getInstance(AES_PADDING);

  }

  /**
   * Encrypt the string with this internal algorithm.
   *
   * @param toBeEncrypt
   *            string object to be encrypt.
   * @return returns encrypted string.
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidAlgorithmParameterException
   * @throws InvalidKeyException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws UnsupportedEncodingException
   */
  public byte[] encrypt(String jsonString) throws InvalidKeyException, InvalidAlgorithmParameterException,
    IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
    byte[] encrypted = cipher.doFinal(jsonString.getBytes("UTF-8"));
    return encrypted;
  }

  /**
   * Decrypt this string with the internal algorithm. The passed argument
   * should be encrypted using {@link #encrypt(String) encrypt} method of this
   * class.
   *
   * @param encrypted
   *            encrypted string that was encrypted using
   *            {@link #encrypt(String) encrypt} method.
   * @return decrypted string.
   * @throws InvalidAlgorithmParameterException
   * @throws InvalidKeyException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   */
  public String decrypt(byte[] encryptedJsonString) throws InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
    byte[] decryptedBytes = cipher.doFinal(encryptedJsonString);
    return new String(decryptedBytes);
  }

//  public static void main(String args[]) throws Exception{
//    AESServiceImpl c= new AESServiceImpl();
//    FileOutputStream out=new FileOutputStream(new File("Test.txt"));
//    //out.write(c.encrypt(NotificationConstants.PASSWORD));
//    //out.write(Base64.getEncoder().encode(c.encrypt(NotificationConstants.PASSWORD)));
//    //
//    System.out.println(c.decrypt(Base64.getDecoder().decode("iE473gCIi1DLrmbG810TzA==")));
//
//    out.close();
//    //System.out.println(Base64.getEncoder().encode(c.encrypt(NotificationConstants.PASSWORD)));
//
//  }


}
