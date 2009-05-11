package com.monsur.boxqueue.data;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
@EmbeddedOnly
public class BoxeeAlternativeLink {

  @Persistent
  private String label;

  @Persistent
  private String thumb;

  @Persistent
  private String boxeeAlternativeLinkUrl;

  public BoxeeAlternativeLink() {
    label = "";
    thumb = "";
    boxeeAlternativeLinkUrl = "";
  }

  public boolean exists() {
    return label != null && thumb != null && boxeeAlternativeLinkUrl != null && label != "" &&
        thumb != "" && boxeeAlternativeLinkUrl != "";
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getUrl() {
    return boxeeAlternativeLinkUrl;
  }

  public void setUrl(String url) {
    this.boxeeAlternativeLinkUrl = url;
  }
}
