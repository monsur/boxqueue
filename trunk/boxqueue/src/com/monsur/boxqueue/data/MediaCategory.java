package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class MediaCategory {

  @Persistent
  private String mediaCategoryScheme;

  @Persistent
  private Text mediaCategoryValue;

  public String getScheme() {
    return mediaCategoryScheme;
  }

  public void setScheme(String scheme) {
    this.mediaCategoryScheme = scheme;
  }

  public String getValue() {
    return mediaCategoryValue.getValue();
  }

  public void setValue(String value) {
    this.mediaCategoryValue = new Text(value);
  }
}
