package com.monsur.boxqueue.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monsur.boxqueue.adaptor.AdaptorException;
import com.monsur.boxqueue.adaptor.VideoAdaptor;
import com.monsur.boxqueue.adaptor.VideoAdaptorFactory;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.UrlWithQuery;

/**
 * Servlet used only to test shit out.
 * @author monsur
 *
 */
@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("text/plain");

    String urlString = request.getParameter("url");
//    String urlString = "http://dev-tools.appspot.com/httpresponsecodes/200";

    UrlWithQuery url;
    try {
      url = new UrlWithQuery(urlString);
    } catch (MalformedURLException muex) {
      throw new IOException("invalid url = " + urlString);
    }

    VideoAdaptor adaptor = VideoAdaptorFactory.create(url);

    List<UserItem> items = new ArrayList<UserItem>();
    try {
      items = adaptor.load();
    } catch (AdaptorException e) {
      e.printStackTrace();
    }

    response.getWriter().println("Found " + items.size() + " item" +
        (items.size() == 1 ? "" : "s"));
    for (UserItem item : items) {
      response.getWriter().println("\t" + item.getTitle() + ": " + item.getMediaContent().getUrl());
    }
  }
}
