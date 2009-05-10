package com.monsur.boxqueue.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;

// TODO(monsur): add indexes
public class DataHelper {

  private PersistenceManager pm;
  private List<Query> queries;
  private boolean opened = false;

  public DataHelper() {
    queries = new ArrayList<Query>();
  }

  public void open() {
    if (opened) {
      // TODO(monsur): make this a custom exception
      throw new IllegalArgumentException("Alredy opened.");
    }
    pm = PMF.get().getPersistenceManager();
    opened = true;
  }

  public void close() {
    if (!opened) {
      return;
    }
    for (Query query : queries) {
      query.closeAll();
    }
    queries.clear();
    pm.close();
  }

  public UserFeed getUserFeed(User user) {
    Query query = pm.newQuery(UserFeed.class);
    query.setFilter("user == userParam");
    query.declareParameters("com.google.appengine.api.users.User userParam");
    List<UserFeed> results = (List<UserFeed>) query.execute(user);
    queries.add(query);
    if (results.iterator().hasNext()) {
      return results.get(0);
    }
    return null;
  }

  public UserFeed getUserFeedByPath(String path) {
    Query query = pm.newQuery(UserFeed.class);
    query.setFilter("path == pathParam");
    query.declareParameters("String pathParam");
    List<UserFeed> results = (List<UserFeed>) query.execute(path);
    queries.add(query);
    if (results.iterator().hasNext()) {
      return results.get(0);
    }
    return null;
  }

  public UserFeed createUserFeed(User user) throws IOException {
    UserFeed userFeed = new UserFeed();
    userFeed.setUser(user);
    userFeed.setPath(UserFeed.generatePath(user));
    pm.makePersistent(userFeed);
    return userFeed;
  }

  public List<UserItem> getUnwatchedUserItems(UserFeed feed) {
    Query query = pm.newQuery(UserItem.class);
    query.setFilter("user == userParam && feedId == feedIdParam && watched == watchedParam");
    query.declareParameters("com.google.appengine.api.users.User userParam, Long feedIdParam, boolean watchedParam");
    queries.add(query);
    return (List<UserItem>) query.executeWithArray(feed.getUser(), feed.getId(), false);
  }

  public UserItem createOrUpdate(UserItem item) {
    Query query = pm.newQuery(UserItem.class);
    query.setFilter("user == userParam && feedId == feedIdParam && itemSource == itemSourceParam && sourceId == sourceIdParam");
    query.declareParameters("com.google.appengine.api.users.User userParam, Long feedIdParam, int itemSourceParam, String sourceIdParam");
    // TODO(monsur): make this return one item
    List<UserItem> existingItems = (List<UserItem>) query.executeWithArray(
        item.getUser(), item.getFeedId(), item.getItemSource().ordinal(), item.getSourceId());
    queries.add(query);
    if (existingItems.size() > 0) {
      existingItems.get(0).setDateSort(new Date());
      item = existingItems.get(0);
    } else {
      pm.makePersistent(item);
    }
    return item;
  }

  public UserItem getUserItemByGuid(String guid) {
    Query query = pm.newQuery(UserItem.class);
    query.setFilter("guid == guidParam");
    query.declareParameters("String guidParam");
    // TODO(monsur): make this return one item
    List<UserItem> item = (List<UserItem>) query.execute(guid);
    queries.add(query);
    if (item.size() > 0) {
      return item.get(0);
    }
    return null;
  }
}
