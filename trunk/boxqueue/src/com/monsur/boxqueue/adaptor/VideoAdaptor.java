package com.monsur.boxqueue.adaptor;

import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;

public interface VideoAdaptor {
  UserItem load();
  ItemSource getItemSource();
  String getSourceId();
}
