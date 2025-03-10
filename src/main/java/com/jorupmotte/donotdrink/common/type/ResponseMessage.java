package com.jorupmotte.donotdrink.common.type;

public interface ResponseMessage {
  // common
  String SUCCESS = "SUCCESS";
  String VALIDATION_FAIL =  "Validation failed";
  String DATABASE_ERROR = "Database error";
  String AUTHORIZATION_FAIL = "Authorization failed";

  // user
  String DUPLICATE_ID =  "Duplicate id";
  String DUPLICATE_EMAIL =  "Duplicate email";
  String NO_SESSION_INFO =  "Can't find the session information";
  String SIGN_IN_FAIL = "Login information mistmatch";
  String NO_EMAIL = "Can't find the email address";
  String MAIL_SEND_FAIL = "Failed to send an email";
  String VERIFICATION_FAIL = "Verification failed";

  // user
  String NOT_FRIEND = "Not a friend";

  // budget
  String BUDGET_UNDEFINED = "Budget is not defined";
  String BUDGET_ALREADY_DEFINED = "Budget is already defined";
  String TOO_MANY_REQUESTS = "Too many requests";

  // friend
  String USER_NOT_FOUND = "User not found";
  String ALREADY_FRIEND = "Already a friend";

  // ocr
  String OCR_FAIL = "OCR failed";
}

