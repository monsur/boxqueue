package com.monsur.boxqueue.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletHelper {

  public static void showView(String viewjsp, HttpServlet servlet,
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletContext sc = servlet.getServletContext();
    RequestDispatcher rd = sc.getRequestDispatcher(viewjsp);
    try {
      rd.forward(request, response);
    } catch (ServletException e) {
      servlet.getServletContext().log("ERROR", e);
      e.printStackTrace();
      throw new IOException("Could not show view " + viewjsp);
    }
  }

  public static String getUrl(String path, HttpServletRequest request) {
    StringBuilder url = new StringBuilder();
    url.append(request.getScheme()).append("://")
        .append(request.getServerName());
    if (request.getServerPort() != 80) {
      url.append(":").append(request.getServerPort());
    }
    url.append(path);
    return url.toString();
  }
}
