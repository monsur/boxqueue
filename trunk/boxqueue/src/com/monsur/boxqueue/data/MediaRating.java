package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class MediaRating {

  @Persistent
  private String mediaRatingScheme;

  @Persistent
  private Float mediaRatingValue;

  public String getScheme() {
    return mediaRatingScheme;
  }

  public void setScheme(String scheme) {
    this.mediaRatingScheme = scheme;
  }

  public float getValue() {
    return mediaRatingValue;
  }

  public void setValue(float mediaRatingValue) {
    this.mediaRatingValue = new Float(mediaRatingValue);
  }
}
