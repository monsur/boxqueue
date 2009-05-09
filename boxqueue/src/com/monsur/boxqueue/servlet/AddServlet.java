package com.monsur.boxqueue.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.monsur.boxqueue.adaptor.VideoAdaptor;
import com.monsur.boxqueue.adaptor.VideoAdaptorFactory;
import com.monsur.boxqueue.adaptor.YouTubeAdaptor;
import com.monsur.boxqueue.data.DataHelper;
import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserFeed;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.UrlWithQuery;

public class AddServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("text/plain");

    String urlString = request.getParameter("url");
    String successMsg = request.getParameter("s");
    String errorMsg = request.getParameter("e");

    if (errorMsg != null) {
      request.setAttribute("error", errorMsg);
      ServletHelper.showView("/add.jsp", this, request, response);
      return;
    } else if (successMsg != null) {
      request.setAttribute("success", successMsg);
      ServletHelper.showView("/add.jsp", this, request, response);
      return;
    }

    UrlWithQuery url;
    try {
      url = new UrlWithQuery(urlString);
    } catch (MalformedURLException muex) {
      showError("Invalid url: " + urlString, response);
      return;
    }

    VideoAdaptor adaptor = VideoAdaptorFactory.create(url);
    if (adaptor == null) {
      showError("The url is not supported: " + urlString, response);
      return;
    }

    UserItem userItem = adaptor.load();

    UserService userService = UserServiceFactory.getUserService();

    UserFeed userFeed = DataHelper.loadUserFeed(userService.getCurrentUser());
    if (userFeed == null) {
      userFeed = DataHelper.createUserFeed(userService.getCurrentUser());
    }

    userItem.setItemSource(adaptor.getItemSource());
    userItem.setSourceId(adaptor.getSourceId());
    userItem.setOriginalUrl(urlString);
    userItem.setFeedId(userFeed.getId());
    userItem.setUser(userService.getCurrentUser());

    DataHelper.createOrUpdate(userItem);

    showSuccess("Successfully added \"" + userItem.getTitle() + "\" to Boxqueue!", response);
  }

  private void showError(String msg, HttpServletResponse response) throws IOException {
    redirectToMessage("e", msg, response);
  }

  private void showSuccess(String msg, HttpServletResponse response) throws IOException {
    redirectToMessage("s", msg, response);
  }

  private void redirectToMessage(String qparam, String msg, HttpServletResponse response) throws IOException {
    String redirectUrl = "/private/add?";
    redirectUrl += URLEncoder.encode(qparam, "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");
    response.sendRedirect(redirectUrl);
  }
}
