package com.monsur.boxqueue.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class UserFeed {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;

  @Persistent
  private User user;

  @Persistent
  private Date dateAdded;

  @Persistent
  private Date dateEdited;

  @Persistent
  private Text title;

  @Persistent
  private String path;

  @Persistent
  private Integer boxeeViewOption;

  public UserFeed() {
    dateAdded = new Date();
    dateEdited = new Date();
  }

  public Long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(Date dateAdded) {
    this.dateAdded = dateAdded;
  }

  public Date getDateEdited() {
    return dateEdited;
  }

  public void setDateEdited(Date dateEdited) {
    this.dateEdited = dateEdited;
  }

  public String getTitle() {
    if (title == null) {
      return getDefaultTitle();
    }
    String titleValue = title.getValue();
    if (titleValue == "") {
      return getDefaultTitle();
    }
    return titleValue;
  }

  private String getDefaultTitle() {
    return user.getNickname() + "'s Boxqueue Feed";
  }

  public void setTitle(String title) {
    this.title = new Text(title);
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public BoxeeViewOption getBoxeeViewOption() {
    if (boxeeViewOption == null) {
      return BoxeeViewOption.LINE;
    }
    try {
      return BoxeeViewOption.getById(boxeeViewOption);
    } catch (IllegalArgumentException ex) {
      return BoxeeViewOption.LINE;
    }
  }

  public void setBoxeeViewOption(BoxeeViewOption boxeeViewOption) {
    this.boxeeViewOption = new Integer(boxeeViewOption.getId());
  }

  public static String generatePath() {
    // TODO(monsur): implement this
    throw new UnsupportedOperationException("generatePath() doesn't exist yet");
  }
}
