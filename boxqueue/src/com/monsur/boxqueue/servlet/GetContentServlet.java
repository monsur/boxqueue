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
    if (item == null) {
      // TODO(monsur): need a custom 404 page/error page.
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    String url = item.getMediaContent().getUrl();
    // TODO(monsur): Create cron to purge watched items
    // TODO(monsur): Update watched only if useragent matches boxee
    item.setWatched(true);
    dataHelper.close();
    response.sendRedirect(url);
  }
}
