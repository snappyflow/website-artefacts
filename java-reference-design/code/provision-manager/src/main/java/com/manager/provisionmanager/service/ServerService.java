/**
 * Copyright 2020 MapleLabs
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Server Service to check the status of Workers Servers.
 **/

package com.manager.provisionmanager.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.manager.provisionmanager.entity.Counter;

public class ServerService {

  private static final Logger LOG = LoggerFactory.getLogger(ServerService.class);

  public static Object[] getStatus(String url) throws IOException {

    int code = 200;
    Counter msg = null;
    String result = "", response = "";

    try {

      URL siteURL = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();

      connection.setRequestMethod("GET");
      connection.setConnectTimeout(3000);
      connection.connect();

      code = connection.getResponseCode();

      switch (code) {

      case 200:
      case 201:

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
          sb.append(line + "\n");
        }

        br.close();

        msg = new Gson().fromJson(sb.toString(), Counter.class);

      }

      if (code == 200) {

        result = "Green \t" + "Code : " + code;
        response = "Green";

      } else {

        result = "Yellow \t" + "Code : " + code;
        response = "Yellow";

      }

    } catch (Exception e) {

      result = "Red \t\t" + "Wrong domain : Exception - " + e.getMessage();
      response = "Red";

    }

    LOG.info("\nURL : " + url + "\t Status : " + result + "\nResponse : " + msg);

    return new Object[] { response, msg };

  }

}
