package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class MediaThumbnail {

  @Persistent
  private String mediaThumbnailUrl;

  public String getUrl() {
    return mediaThumbnailUrl;
  }

  public void setUrl(String url) {
    this.mediaThumbnailUrl = url;
  }
}
