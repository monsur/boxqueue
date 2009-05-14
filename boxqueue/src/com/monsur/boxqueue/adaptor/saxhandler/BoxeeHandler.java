package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.List;

import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.UrlWithQuery;

public interface BoxeeHandler {
  List<UserItem> getItems();
  void setUrl(UrlWithQuery url);
}
