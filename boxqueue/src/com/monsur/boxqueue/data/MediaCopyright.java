package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
@EmbeddedOnly
public class MediaCopyright {

  @Persistent
  private Text mediaCopyrightValue;

  public String getValue() {
    return mediaCopyrightValue.getValue();
  }

  public void setValue(String value) {
    this.mediaCopyrightValue = new Text(value);
  }
}
