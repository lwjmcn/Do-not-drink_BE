package com.jorupmotte.donotdrink.common.type;

public interface ResponseCode {
  // common
  String SUCCESS = "SUCCESS";
  String VALIDATION_FAIL = "VLF";
  String DATABASE_ERROR = "DBE";
  String AUTHORIZATION_FAIL = "AUF";

  // auth
  String DUPLICATE_ID = "DI";
  String DUPLICATE_EMAIL = "DE";
  String NO_SESSION_INFO = "NSI";
  String SIGN_IN_FAIL = "SIF";
  String NO_EMAIL = "NE";
  String MAIL_SEND_FAIL = "MSF";
  String VERIFICATION_FAIL = "VRF";

  // user
  String NOT_FRIEND = "NF";

  // budget
  String BUDGET_UNDEFINED = "BU";
  String BUDGET_ALREADY_DEFINED = "BAD";
  String TOO_MANY_REQUESTS = "TMR";

  // friend
  String USER_NOT_FOUND = "UNF";
  String ALREADY_FRIEND = "AF";

  // ocr
  String OCR_FAIL = "OF";
}
