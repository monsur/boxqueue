package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.UrlWithQuery;

public abstract class BaseUrlHandler extends DefaultHandler implements
    BoxeeHandler {

  protected List<UserItem> items;
  protected UrlWithQuery url;

  BaseUrlHandler() {
    items = new ArrayList<UserItem>();
  }

  public void setUrl(UrlWithQuery url) {
    this.url = url;
  }

  public List<UserItem> getItems() {
    return items;
  }

}
