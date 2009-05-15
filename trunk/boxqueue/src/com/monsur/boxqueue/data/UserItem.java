package com.monsur.boxqueue.data;

import java.util.Date;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class UserItem {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private User user;

  @Persistent
  private Long feedId;

  @Persistent
  private Date dateAdded;

  @Persistent
  private Date dateEdited;

  @Persistent
  private Date dateSort;

  @Persistent
  private String originalUrl;

  @Persistent
  private Integer itemSource;

  @Persistent
  private String sourceId;

  @Persistent
  private boolean watched = false;

  @Persistent
  private String guid;

  @Persistent
  private Date pubDate;

  @Persistent
  private String link = "";

  @Persistent
  private Text title;

  @Persistent
  private Text description;

  @Persistent
  @Embedded
  private MediaContent mediaContent;

  @Persistent
  @Embedded
  private MediaThumbnail mediaThumbnail;

  @Persistent
  @Embedded
  private MediaCredit mediaCredit;

  @Persistent
  @Embedded
  private MediaRating mediaRating;

  @Persistent
  @Embedded
  private MediaCopyright mediaCopyright;

  @Persistent
  @Embedded
  private MediaKeywords mediaKeywords;

  @Persistent
  @Embedded
  private MediaCategory mediaCategory;

  @Persistent
  @Embedded
  private BoxeeAlternativeLink boxeeAlternativeLink;

  public UserItem() {
    dateAdded = new Date();
    dateEdited = new Date();
    dateSort = new Date();
    title = new Text("");
    description = new Text("");
    mediaContent = new MediaContent();
    mediaThumbnail = new MediaThumbnail();
    mediaCredit = new MediaCredit();
    mediaRating = new MediaRating();
    mediaCopyright = new MediaCopyright();
    mediaKeywords = new MediaKeywords();
    mediaCategory = new MediaCategory();
    boxeeAlternativeLink = new BoxeeAlternativeLink();
  }

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Long getFeedId() {
    return feedId;
  }

  public void setFeedId(Long feedId) {
    this.feedId = feedId;
  }

  public Date getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(Date dateAdded) {
    this.dateAdded = dateAdded;
  }

  public Date getDateEdited() {
    return dateEdited;
  }

  public void setDateEdited(Date dateEdited) {
    this.dateEdited = dateEdited;
  }

  public Date getDateSort() {
    return dateSort;
  }

  public void setDateSort(Date dateSort) {
    this.dateSort = dateSort;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public ItemSource getItemSource() {
    return ItemSource.values()[itemSource];
  }

  public void setItemSource(ItemSource itemSource) {
    this.itemSource = new Integer(itemSource.ordinal());
  }

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public boolean getWatched() {
    return watched;
  }

  public void setWatched(boolean watched) {
    this.watched = watched;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date pubDate) {
    this.pubDate = pubDate;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getDescription() {
    return description.getValue();
  }

  public void setDescription(String description) {
    this.description = new Text(description);
  }

  public String getTitle() {
    return title.getValue();
  }

  public void setTitle(String title) {
    this.title = new Text(title);
  }

  public MediaThumbnail getMediaThumbnail() {
    return mediaThumbnail;
  }

  public MediaContent getMediaContent() {
    return mediaContent;
  }

  public void setMediaContent(MediaContent mediaContent) {
    this.mediaContent = mediaContent;
  }

  public void setMediaThumbnail(MediaThumbnail mediaThumbnail) {
    this.mediaThumbnail = mediaThumbnail;
  }

  public MediaCredit getMediaCredit() {
    return mediaCredit;
  }

  public void setMediaCredit(MediaCredit mediaCredit) {
    this.mediaCredit = mediaCredit;
  }

  public MediaRating getMediaRating() {
    return mediaRating;
  }

  public void setMediaRating(MediaRating mediaRating) {
    this.mediaRating = mediaRating;
  }

  public MediaCopyright getMediaCopyright() {
    return mediaCopyright;
  }

  public void setMediaCopyright(MediaCopyright mediaCopyright) {
    this.mediaCopyright = mediaCopyright;
  }

  public MediaKeywords getMediaKeywords() {
    return mediaKeywords;
  }

  public void setMediaKeywords(MediaKeywords mediaKeywords) {
    this.mediaKeywords = mediaKeywords;
  }

  public MediaCategory getMediaCategory() {
    return mediaCategory;
  }

  public void setMediaCategory(MediaCategory mediaCategory) {
    this.mediaCategory = mediaCategory;
  }

  public BoxeeAlternativeLink getBoxeeAlternativeLink() {
    return boxeeAlternativeLink;
  }

  public void setBoxeeAlternativeLink(BoxeeAlternativeLink boxeeAlternativeLink) {
    this.boxeeAlternativeLink = boxeeAlternativeLink;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ITEM\r\n");
    if (title != null) {
      sb.append("\ttitle = ").append(title.getValue()).append("\r\n");
    }
    if (description != null && description.getValue() != null) {
      sb.append("\tdescription = ").append(description.getValue()).append("\r\n");
    }
    if (mediaKeywords != null && mediaKeywords.exists()) {
      sb.append("\tkeywords = ").append(mediaKeywords.getValue()).append("\r\n");
    }
    if (mediaContent != null && mediaContent.exists()) {
      sb.append("\tcontent url = ").append(mediaContent.getUrl()).append("\r\n");
    }
    if (mediaThumbnail != null && mediaThumbnail.exists()) {
      sb.append("\tthumbnail = ").append(mediaThumbnail.getUrl()).append("\r\n");
    }
    sb.append("\r\n");
    return sb.toString();
  }
}
