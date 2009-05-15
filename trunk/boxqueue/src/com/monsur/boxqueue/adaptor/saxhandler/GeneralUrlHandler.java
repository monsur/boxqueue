package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.HelperMethods;

public class GeneralUrlHandler extends BaseUrlHandler implements BoxeeHandler {

  private boolean titleMarker = false;
  private String title = "";
  private Stack<SimpleElement> stack;
  private List<SimpleElement> elements;

  public GeneralUrlHandler() {
    stack = new Stack<SimpleElement>();
    elements = new ArrayList<SimpleElement>();
  }

  @Override
  public ItemSource getItemSource() {
    return ItemSource.GENERAL;
  }

  @Override
  public void startElement(String nsURI, String strippedName, String tagName,
      Attributes attributes) throws SAXException {
    if (tagName.equalsIgnoreCase("object") || tagName.equalsIgnoreCase("embed") || tagName.equalsIgnoreCase("param")) {
      SimpleElement element = new SimpleElement();
      element.setName(tagName.toLowerCase());
      element.setAttributes(getAttributes(attributes));
      stack.add(element);
    } else if (tagName.equalsIgnoreCase("title")) {
      titleMarker = true;
    }
  }

  @Override
  public void endElement( String uri, String localName, String tagName ) {
    if (stack.size() == 0) {
      return;
    }
    SimpleElement tmp = stack.pop();
    if (tagName.equalsIgnoreCase("object") || tagName.equalsIgnoreCase("embed")) {
      elements.add(tmp);
    } else if (tagName.equalsIgnoreCase("param")) {
      SimpleElement tmp2 = stack.pop();
      if (tmp2.getName().equals("object") || tmp2.getName().equals("embed")) {
        tmp2.getChildren().add(tmp);
      }
      stack.push(tmp2);
    } else {
      stack.push(tmp);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    if (titleMarker) {
      title = new String(ch, start, length);
      titleMarker = false;
    }
  }

  @Override
  public void endDocument() {
    while (stack.size() > 0) {
      SimpleElement tmp = stack.pop();
      if (tmp.getName().equals("object") || tmp.getName().equals("embed")) {
        elements.add(tmp);
      }
    }
    // TODO(monsur): Process object tags before embed tags?
    // TODO(monsur): These videos don't autoplay
    items = new ArrayList<UserItem>();
    Map<String, Boolean> urlHash = new HashMap<String, Boolean>();
    System.out.println("Number of elements = " + elements.size());
    for (SimpleElement element : elements) {
      System.out.println(element.toString());
      UserItem item = null;
      if (element.getName().equals("object")) {
        item = getUserItemFromObject(element);
      } else if (element.getName().equals("embed")) {
        item = getUserItemFromEmbed(element);
      }
      if (item == null) {
        continue;
      }
      if (urlHash.containsKey(item.getMediaContent().getUrl())) {
        continue;
      } else {
        urlHash.put(item.getMediaContent().getUrl(), true);
      }
      if (item.getTitle() == null || item.getTitle() == "") {
        if (items.size() == 0) {
          item.setTitle(title);
        } else {
          item.setTitle(title + " (" + items.size() + ")");
        }
      }
      items.add(item);
    }
  }

  private UserItem getUserItemFromEmbed(SimpleElement element) {
    String embedType = element.getAttributes().get("type");
    String embedSrc = element.getAttributes().get("src");
    String flashvars = element.getAttributes().get("flashvars");
    if (embedType == null || !embedType.equals("application/x-shockwave-flash")) {
      return null;
    }
    if (embedSrc == null) {
      return null;
    }
    if (flashvars != null) {
      embedSrc += (embedSrc.indexOf("?") > 0 ? "&" : "?") + flashvars;
    }
    UserItem item = new UserItem();
    item.getMediaContent().setType(embedType);
    item.getMediaContent().setUrl(HelperMethods.getFullUrl(embedSrc, url));
    return item;
  }

  private UserItem getUserItemFromObject(SimpleElement element) {
    String objectType = element.getAttributes().get("type");
    String objectData = element.getAttributes().get("data");
    String paramMovie = element.getParam("movie");
    String flashvars = element.getParam("flashvars");
    String mediaContent = objectData != null ? objectData : paramMovie;
    if (objectType == null || !objectType.equals("application/x-shockwave-flash")) {
      return null;
    }
    if (mediaContent == null) {
      return null;
    }
    if (flashvars != null) {
      mediaContent += (mediaContent.indexOf("?") > 0 ? "&" : "?") + flashvars;
    }
    UserItem item = new UserItem();
    item.getMediaContent().setType(objectType);
    item.getMediaContent().setUrl(HelperMethods.getFullUrl(mediaContent, url));
    return item;
  }
}
