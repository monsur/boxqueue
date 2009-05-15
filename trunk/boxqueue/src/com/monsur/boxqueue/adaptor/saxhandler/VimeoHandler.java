package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;

public class VimeoHandler extends BaseUrlHandler implements BoxeeHandler {

  private boolean inHead = false;
  private String title;
  private String description;
  private String videoType;
  private String keywords;
  private String thumbnail;
  private String videosrc;

  public VimeoHandler() {
  }

  @Override
  public ItemSource getItemSource() {
    return ItemSource.VIMEO;
  }

  @Override
  public void startElement(String nsURI, String strippedName, String tagName,
      Attributes attributes) throws SAXException {
    if (tagName.equalsIgnoreCase("head")) {
      inHead = true;
      return;
    }
    if (inHead == false) {
      return;
    }
    if (tagName.equalsIgnoreCase("meta")) {
      Map<String, String> attrs = getAttributes(attributes);
      String name = attrs.get("name");
      if (name == null) {
        return;
      }
      if (name.equalsIgnoreCase("title")) {
        title = attrs.get("content");
      } else if (name.equalsIgnoreCase("description")) {
        description = attrs.get("content");
      } else if (name.equalsIgnoreCase("video_type")) {
        videoType = attrs.get("content");
      } else if (name.equalsIgnoreCase("keywords")) {
        keywords = attrs.get("content");
      }
    } else if (tagName.equalsIgnoreCase("link")) {
      Map<String, String> attrs = getAttributes(attributes);
      String rel = attrs.get("rel");
      if (rel == null) {
        return;
      }
      if (rel.equalsIgnoreCase("videothumbnail")) {
        thumbnail = attrs.get("href");
      } else if (rel.equalsIgnoreCase("video_src")) {
        videosrc = attrs.get("href");
      }
    }
  }

  @Override
  public void endElement( String uri, String localName, String tagName ) {
    if (tagName.equalsIgnoreCase("head")) {
      // TODO(monsur): Is there a way to just end processing here?
      inHead = false;
      return;
    }
  }

  @Override
  public void endDocument() {
    if (videosrc == null) {
      System.out.println("no video src");
      return;
    }
    if (!videoType.equalsIgnoreCase("application/x-shockwave-flash")) {
      System.out.println("video type = " + videoType);
      return;
    }
    UserItem item = new UserItem();
    if (title == null) {
      title = "Vimeo video";
    }
    videosrc += (videosrc.indexOf("?") > 0 ? "&" : "?") + "autoplay=1";
    item.setTitle(title);
    item.setDescription(description);
    item.getMediaKeywords().setValue(keywords);
    item.getMediaContent().setUrl(videosrc);
    item.getMediaContent().setType(videoType);
    item.getMediaThumbnail().setUrl(thumbnail);
    items.add(item);
  }
}
