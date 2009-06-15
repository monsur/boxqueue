package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
@EmbeddedOnly
public class MediaContent {

  @Persistent
  private String mediaContentUrl;
  @Persistent
  private Text mediaContentUrl2;

  @Persistent
  private String type;

  @Persistent
  private Integer duration;

  public boolean exists() {
    return (mediaContentUrl != null && mediaContentUrl != "") || (mediaContentUrl2 != null &&
        mediaContentUrl2.getValue() != "");
  }

  public String getUrl() {
    if (mediaContentUrl2 != null) {
      return mediaContentUrl2.getValue();
    } else if (mediaContentUrl != null) {
      return mediaContentUrl;
    }
    return "";
  }

  public void setUrl(String url) {
    this.mediaContentUrl2 = new Text(url);
    this.mediaContentUrl = null;
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
