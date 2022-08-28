package com.key.oa.common;

/**
 * BadCredentialsException在不同场景下的异常信息
 * @author 孙强
 */
public class BadCredentialsMessage {
   /**
    * 密码不正确的情况，也是BadCredentialsException的默认信息
    */
   public static final String PASSWORD_WRONG = "Bad credentials";

   /**
    * Token找不到的情况
    */
   public static final String TOKEN_NOT_FOUND = "Token not found";
}
