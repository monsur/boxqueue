package com.monsur.boxqueue.adaptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.extensions.Rating;
import com.google.gdata.data.media.mediarss.MediaCredit;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.util.ServiceException;
import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.UrlWithQuery;

public class YouTubeAdaptor implements VideoAdaptor {

  private String sourceId;

  YouTubeAdaptor() {
  }

  public ItemSource getItemSource() {
    return ItemSource.YOUTUBE;
  }

  public String getSourceId() {
    return sourceId;
  }

  public List<UserItem> load() throws AdaptorException {
    List<UserItem> items = new ArrayList<UserItem>();
    if (sourceId == null) {
      return items;
    }
    try {
      YouTubeService service = new YouTubeService("ytapi-MonsurHossain-Boxqueue-pnpi1grg-1", "AI39si4_NEFnB-8RfSbrESihEjRi5nhatxikhRhEVfatjhNgwceV3zeMTgvIUsjuMORsYs1awMla_-tIpeOWZ9ISwo-rpke3eA");
      String videoEntryUrl = "http://gdata.youtube.com/feeds/api/videos/" + URLEncoder.encode(sourceId, "UTF-8");
      VideoEntry videoEntry = service.getEntry(new URL(videoEntryUrl), VideoEntry.class);
      UserItem item = getUserItem(videoEntry);
      if (item != null) {
        items.add(item);
      }
    } catch (UnsupportedEncodingException e) {
      throw new AdaptorException(e);
    } catch (MalformedURLException e) {
      throw new AdaptorException(e);
    } catch (IOException e) {
      throw new AdaptorException(e);
    } catch (ServiceException e) {
      throw new AdaptorException(e);
    }
    return items;
  }

  private UserItem getUserItem(VideoEntry entry) {
    UserItem userItem = new UserItem();

    userItem.setTitle(entry.getTitle().getPlainText());

    userItem.setPubDate(new java.util.Date(entry.getPublished().getValue()));

    YouTubeMediaGroup mediaGroup = entry.getMediaGroup();

    userItem.setDescription(mediaGroup.getDescription().getPlainTextContent());

    StringBuilder sbkeywords = new StringBuilder();
    MediaKeywords keywords = mediaGroup.getKeywords();
    for (String keyword : keywords.getKeywords()) {
      if (sbkeywords.length() > 0) {
        sbkeywords.append(",");
      }
      sbkeywords.append(keyword);
    }
    userItem.getMediaKeywords().setValue(sbkeywords.toString());

    for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
      if (mediaContent.getType().equals("application/x-shockwave-flash")) {
        userItem.getMediaContent().setType(mediaContent.getType());
        userItem.getMediaContent().setDuration(mediaContent.getDuration());
        userItem.getMediaContent().setUrl(mediaContent.getUrl());
      }
    }

    if (mediaGroup.getThumbnails().size() > 0) {
      userItem.getMediaThumbnail().setUrl(mediaGroup.getThumbnails().get(0).getUrl());
    }

    if (mediaGroup.getCredits().size() > 0) {
      MediaCredit mediaCredit = mediaGroup.getCredits().get(0);
      userItem.getMediaCredit().setScheme(mediaCredit.getScheme());
      userItem.getMediaCredit().setRole(mediaCredit.getRole());
      userItem.getMediaCredit().setValue(mediaCredit.getContent());
    }

    Rating rating = entry.getRating();
    if (rating != null) {
      userItem.getMediaRating().setScheme("urn:user");
      userItem.getMediaRating().setValue(rating.getAverage() * 2);
    }

    return userItem;
  }

  public static VideoAdaptor create(UrlWithQuery url) {
    YouTubeAdaptor source = new YouTubeAdaptor();
    String sourceId = url.getQueryParameter("v");
    if (sourceId == null) {
      // match youtube urls in the form http://www.youtube.com/v/rB4R6oMkrsw
      Pattern pattern1 = Pattern.compile("http://www\\.youtube\\.com/v/([a-zA-Z0-9]+)");
      Matcher matcher = pattern1.matcher(url.getOriginalUrl());
      if (matcher.find()) {
        sourceId = matcher.group(1);
      }
    }
    source.sourceId = sourceId;
    return source;
  }
}
