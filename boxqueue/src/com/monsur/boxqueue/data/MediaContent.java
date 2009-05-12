package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
@EmbeddedOnly
public class MediaContent {

  @Persistent
  private String mediaContentUrl;

  @Persistent
  private String type;

  @Persistent
  private Integer duration;

  public boolean exists() {
    return mediaContentUrl != null && mediaContentUrl != "";
  }

  public String getUrl() {
    return mediaContentUrl;
  }

  public void setUrl(String url) {
    this.mediaContentUrl = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = new Integer(duration);
  }
}
