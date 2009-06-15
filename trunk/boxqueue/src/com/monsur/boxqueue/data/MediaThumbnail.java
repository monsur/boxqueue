package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
@EmbeddedOnly
public class MediaThumbnail {

  @Persistent
  private String mediaThumbnailUrl;
  @Persistent
  private Text mediaThumbnailUrl2;

  public boolean exists() {
    return (mediaThumbnailUrl != null && mediaThumbnailUrl != "") || (mediaThumbnailUrl2 != null &&
        mediaThumbnailUrl2.getValue() != "");
  }

  public String getUrl() {
    if (mediaThumbnailUrl2 != null) {
      return mediaThumbnailUrl2.getValue();
    } else if (mediaThumbnailUrl != null) {
      return mediaThumbnailUrl;
    }
    return "";
  }

  public void setUrl(String url) {
    this.mediaThumbnailUrl2 = new Text(url);
    this.mediaThumbnailUrl = null;
  }
}
