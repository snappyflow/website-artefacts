/**
 * Copyright 2020 MapleLabs
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Common application constants.
 **/

package com.manager.provisionmanager.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application.properties")
@Service
public class AppConstants {

  private static final Logger LOG = LoggerFactory.getLogger(AppConstants.class);

  public static List<String> WORKER_GET_URI_LIST = new ArrayList<String>();
  public static List<String> WORKER_RESET_URI_LIST = new ArrayList<String>();

  public static List<String> WORKER_SERVER_IP;

  public static String WORKER_GET_URI_PATH = "";
  public static String WORKER_RESET_URI_PATH = "";

  AppConstants(@Value("${worker.server.ip}") List<String> WORKER_SERVER_IP,

    @Value("${worker.get.uri.path}") String WORKER_GET_URI_PATH,

    @Value("${worker.reset.uri.path}") String WORKER_RESET_URI_PATH) {

    AppConstants.WORKER_SERVER_IP = WORKER_SERVER_IP;

    AppConstants.WORKER_GET_URI_PATH = WORKER_GET_URI_PATH;

    AppConstants.WORKER_RESET_URI_PATH = WORKER_RESET_URI_PATH;

    generateUriList();

  }

  public static void generateUriList() {

    for (String i : WORKER_SERVER_IP) {

      if (i.contains("http://") || i.contains("https://")) {

        LOG.info(i + " have HTTP details ... ");

        AppConstants.WORKER_GET_URI_LIST.add(i + AppConstants.WORKER_GET_URI_PATH);
        AppConstants.WORKER_RESET_URI_LIST.add(i + AppConstants.WORKER_RESET_URI_PATH);

      } else {

        LOG.info(i + " don't have HTTP details ... ");

        AppConstants.WORKER_GET_URI_LIST.add("http://" + i + AppConstants.WORKER_GET_URI_PATH);
        AppConstants.WORKER_RESET_URI_LIST.add("http://" + i + AppConstants.WORKER_RESET_URI_PATH);

      }

    }

  }

}
