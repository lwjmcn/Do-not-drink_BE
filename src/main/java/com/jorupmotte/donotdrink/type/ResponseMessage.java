package com.jorupmotte.donotdrink.type;

public interface ResponseMessage {
  String SUCCESS = "SUCCESS";
  String VALIDATION_FAIL =  "Validation failed";
  String DUPLICATE_ID =  "Duplicate id";
  String DUPLICATE_EMAIL =  "Duplicate email";
  String NO_SESSION_INFO =  "Can't find the session information";
  String SIGN_IN_FAIL = "Login information mistmatch";
  String NO_EMAIL = "Can't find the email address";
  String MAIL_SEND_FAIL = "Failed to send an email";
  String VERIFICATION_FAIL = "Verification failed";
  String DATABASE_ERROR = "Database error";
} 
