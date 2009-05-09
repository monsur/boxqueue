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
    return title.getValue();
  }

  public void setTitle(String title) {
    this.title = new Text(title);
  }
}
