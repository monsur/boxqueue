package com.monsur.boxqueue.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.google.common.collect.Maps;

public class UrlWithQuery {

  private URL url;
  private Map<String, String> queryparameters;
  private String originalUrl;

  public UrlWithQuery(String urlString) throws MalformedURLException {
    this.originalUrl = urlString;
    url = new URL(urlString);
    queryparameters = Maps.newHashMap();
    if (url.getQuery() != null) {
      String[] params = url.getQuery().split("&");
      for (String param : params) {
        String[] pair = param.split("=");
        queryparameters.put(pair[0], pair[1]);
      }
    }
  }

  public String getHost() {
    return url.getHost();
  }

  public String getQueryParameter(String name) {
    return queryparameters.get(name);
  }

  public String getOriginalUrl() {
    return originalUrl;
  }
}
