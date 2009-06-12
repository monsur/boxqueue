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
        if (pair.length == 2) {
          queryparameters.put(pair[0], pair[1]);
        } else if (pair.length == 1) {
          queryparameters.put(pair[0], "");
        }
      }
    }
  }

  public String getHost() {
    return url.getHost();
  }

  public String getAuthority() {
    return url.getAuthority();
  }

  public String getPath() {
    return url.getPath();
  }

  public String getPathDirectory() {
    return getPath().substring(0, getPath().lastIndexOf('/') + 1);
  }

  public String getProtocol() {
    return url.getProtocol();
  }

  public String getQueryParameter(String name) {
    return queryparameters.get(name);
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public URL getJavaNetUrl() {
    return url;
  }
}
