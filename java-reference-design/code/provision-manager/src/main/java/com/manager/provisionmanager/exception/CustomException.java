/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Aanchal Sathyanarayanan (aanchal.sathyanarayanan@maplelabs.com)
 * 
 * Description: Custom exception for incorrect JSON.
 **/

package com.manager.provisionmanager.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomException extends ResponseEntityExceptionHandler {

  private String errorMessage;

  public CustomException(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

}
