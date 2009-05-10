package com.monsur.boxqueue.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.monsur.boxqueue.data.DataHelper;
import com.monsur.boxqueue.data.UserFeed;

public class ManageServlet extends HttpServlet {

  private static final long serialVersionUID = 7007079501928267286L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    DataHelper dataHelper = new DataHelper();
    dataHelper.open();
    UserFeed userFeed = dataHelper.getUserFeed(userService.getCurrentUser());
    if (userFeed == null) {
      userFeed = dataHelper.createUserFeed(userService.getCurrentUser());
    }
    request.setAttribute("feedurl", ServletHelper.getUrl("/feed/" + URLEncoder.encode(userFeed.getPath(), "UTF-8"), request));
    dataHelper.close();

    String bookmarklet = "javascript:(function(){s=document.createElement('SCRIPT');s.type='text/javascript';s.src='" + ServletHelper.getUrl("", request) + "/static/js/add.js?x='+(Math.random());document.getElementsByTagName('head')[0].appendChild(s);})();";
    request.setAttribute("bookmarklet", bookmarklet);

    ServletHelper.showView("/manage.jsp", this, request, response);
  }
}
