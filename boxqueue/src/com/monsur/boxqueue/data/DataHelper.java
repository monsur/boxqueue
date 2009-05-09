package com.monsur.boxqueue.data;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;

public class DataHelper {

  public static UserFeed loadUserFeed(User user) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Query query = pm.newQuery(UserFeed.class);
    query.setFilter("user == userParam");
    query.declareParameters("com.google.appengine.api.users.User userParam");
    try {
      List<UserFeed> results = (List<UserFeed>) query.execute(user);
      if (results.iterator().hasNext()) {
        return results.get(0);
      }
    } finally {
      query.closeAll();
    }
    return null;
  }

  public static UserFeed createUserFeed(User user) {
    PersistenceManager pm = PMF.get().getPersistenceManager();

    UserFeed userFeed = new UserFeed();
    userFeed.setUser(user);

    try {
        pm.makePersistent(userFeed);
    } finally {
        pm.close();
    }
    return userFeed;
  }

  public static UserItem createOrUpdate(UserItem item) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      Query query = pm.newQuery(UserItem.class);
      query.setFilter("user == userParam && feedId == feedIdParam && itemSource == itemSourceParam && sourceId == sourceIdParam");
      query.declareParameters("com.google.appengine.api.users.User userParam, Long feedIdParam, int itemSourceParam, String sourceIdParam");
      List<UserItem> existingItems = (List<UserItem>) query.executeWithArray(
          item.getUser(), item.getFeedId(), item.getItemSource().ordinal(), item.getSourceId());
      if (existingItems.size() > 0) {
        existingItems.get(0).setDateSort(new Date());
        item = existingItems.get(0);
      } else {
        pm.makePersistent(item);
      }
    } finally {
      pm.close();
    }
    return item;
  }
}
