package com.monsur.boxqueue.adaptor;

import java.util.List;

import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;;

// TODO(monsur): Rename to Adapter, spell correctly
public interface VideoAdaptor {
  List<UserItem> load() throws AdaptorException;
  ItemSource getItemSource();
  String getSourceId();
}
