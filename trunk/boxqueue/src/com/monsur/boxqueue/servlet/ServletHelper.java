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
    RequestDispatcher rd = sc.getRequestDispatcher("/add.jsp");
    try {
      rd.forward(request, response);
    } catch (ServletException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
