package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class MediaCredit {

  @Persistent
  private String role;

  @Persistent
  private String mediaCreditScheme;

  @Persistent
  private String mediaCreditValue;

  public String getScheme() {
    return mediaCreditScheme;
  }

  public void setScheme(String mediaCreditScheme) {
    this.mediaCreditScheme = mediaCreditScheme;
  }

  public String getValue() {
    return mediaCreditValue;
  }

  public void setValue(String mediaCreditValue) {
    this.mediaCreditValue = mediaCreditValue;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
