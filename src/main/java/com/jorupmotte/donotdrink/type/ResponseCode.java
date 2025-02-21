package com.jorupmotte.donotdrink.type;

public interface ResponseCode {
  String SUCCESS = "SUCCESS";
  String VALIDATION_FAIL = "VLF";
  String DUPLICATE_ID = "DI";
    String DUPLICATE_EMAIL = "DE";
  String SIGN_IN_FAIL = "SIF";
  String NO_EMAIL = "NE";
  String MAIL_SEND_FAIL = "MSF";
  String VERIFICATION_FAIL = "VRF";
  String DATABASE_ERROR = "DBE"; 
}
