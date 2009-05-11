package com.monsur.boxqueue.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.monsur.boxqueue.adaptor.AdaptorException;
import com.monsur.boxqueue.adaptor.VideoAdaptor;
import com.monsur.boxqueue.adaptor.VideoAdaptorFactory;
import com.monsur.boxqueue.data.DataHelper;
import com.monsur.boxqueue.data.UserFeed;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.HelperMethods;
import com.monsur.boxqueue.util.UrlWithQuery;

public class AddServlet extends HttpServlet {

  private static final long serialVersionUID = -5001987995408331518L;

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

    List<UserItem> items = new ArrayList<UserItem>();
    try {
      // TODO(monsur): remove this try catch and handle the error properly
      items = adaptor.load();
    } catch (AdaptorException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    UserItem userItem = null;
    if (items.size() > 0) {
      userItem = items.get(0);
    }

    UserService userService = UserServiceFactory.getUserService();

    DataHelper dataHelper = new DataHelper();
    dataHelper.open();
    try {
      UserFeed userFeed = dataHelper.getUserFeed(userService.getCurrentUser());
      if (userFeed == null) {
        userFeed = dataHelper.createUserFeed(userService.getCurrentUser());
      }

      // TODO(monsur): what if media:content or other things are null?
      userItem.setItemSource(adaptor.getItemSource());
      userItem.setSourceId(adaptor.getSourceId());
      userItem.setOriginalUrl(urlString);
      userItem.setFeedId(userFeed.getId());
      userItem.setUser(userService.getCurrentUser());
      userItem.setGuid(generateGuid(userItem));

      dataHelper.createOrUpdate(userItem);
    } finally {
      dataHelper.close();
    }
    showSuccess("Successfully added \"" + userItem.getTitle() + "\" to Boxqueue!", response);
  }

  private String generateGuid(UserItem userItem) throws IOException {
    Random r = new Random();
    String guidString = userItem.getUser().getNickname()
        + "-" + userItem.getFeedId()
        + "-" + userItem.getItemSource().toString()
        + "-" + userItem.getSourceId()
        + "-" + r.nextInt();
    return HelperMethods.hash(guidString);
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
