package com.monsur.boxqueue.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monsur.boxqueue.data.DataHelper;
import com.monsur.boxqueue.data.UserItem;

public class GetContentServlet extends HttpServlet {

  private static final long serialVersionUID = 1174544784147342438L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String guid = request.getParameter("guid");
    DataHelper dataHelper = new DataHelper();
    dataHelper.open();
    UserItem item = dataHelper.getUserItemByGuid(guid);
    String url = item.getMediaContent().getUrl();
    // TODO(monsur): Create cron to purge watched itemss
    item.setWatched(true);
    dataHelper.close();
    response.sendRedirect(url);
  }
}
