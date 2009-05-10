package com.monsur.boxqueue.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monsur.boxqueue.data.DataHelper;
import com.monsur.boxqueue.data.UserFeed;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.formatter.BaseFormatter;
import com.monsur.boxqueue.formatter.BoxeeFeedFormatter;
import com.monsur.boxqueue.formatter.FormatterException;

public class FeedServlet extends HttpServlet {

  private static final long serialVersionUID = 3523458428909602956L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/rss+xml");
    response.setCharacterEncoding("UTF-8");

    String path = getPathFromRequestUrl(request);

    DataHelper dataHelper = new DataHelper();
    dataHelper.open();
    try {
      UserFeed feed = dataHelper.getUserFeedByPath(path);
      if (feed == null) {
        response.sendError(response.SC_NOT_FOUND);
        return;
      }

      List<UserItem> items = dataHelper.getUnwatchedUserItems(feed);

      BaseFormatter formatter = new BoxeeFeedFormatter();
      try {
        formatter.getOutput(feed, items, request, response);
      } catch (FormatterException ex) {
        response.sendError(response.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
      }
    } finally {
      dataHelper.close();
    }
}

  private String getPathFromRequestUrl(HttpServletRequest request) {
    return request.getPathInfo().substring(1);
  }
}
