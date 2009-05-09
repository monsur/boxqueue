package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
@EmbeddedOnly
public class MediaKeywords {

  @Persistent
  private Text mediaKeywordsValue;

  public MediaKeywords() {
  }

  public String getValue() {
    return mediaKeywordsValue.getValue();
  }

  public void setValue(String value) {
    this.mediaKeywordsValue = new Text(value);
  }
}
