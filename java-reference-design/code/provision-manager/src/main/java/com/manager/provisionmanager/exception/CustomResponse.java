/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Custom Response for APIs.
 **/

package com.manager.provisionmanager.exception;

public class CustomResponse {

  public String message;

  public CustomResponse(String message) {
    this.message = message;
  }

  public CustomResponse() {
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Response {" + "message=" + message + "}";
  }

}
