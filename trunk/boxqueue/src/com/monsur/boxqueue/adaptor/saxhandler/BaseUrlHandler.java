package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.monsur.boxqueue.data.ItemSource;
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

  public ItemSource getItemSource() {
    return ItemSource.NONE;
  }

  protected Map<String, String> getAttributes(Attributes attributes) {
    Map<String, String> item = new HashMap<String, String>();
    for (int i = 0; i < attributes.getLength(); i++) {
      item.put(attributes.getLocalName(i), attributes.getValue(i));
    }
    return item;
  }
}
