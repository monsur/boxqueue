package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
@EmbeddedOnly
public class MediaThumbnail {

  @Persistent
  private String mediaThumbnailUrl = "";

  public boolean exists() {
    return mediaThumbnailUrl != null && mediaThumbnailUrl != "";
  }

  public String getUrl() {
    return mediaThumbnailUrl;
  }

  public void setUrl(String url) {
    this.mediaThumbnailUrl = url;
  }
}
