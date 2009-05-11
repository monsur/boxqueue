package com.monsur.boxqueue.adaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class VideoSaxHandler extends DefaultHandler {

  private boolean titleMarker = false;

  private String title = "";
  private List<Map<String, String>> items;

  public VideoSaxHandler() {
    items = new ArrayList<Map<String, String>>();
  }

  public String getTitle() {
    return title;
  }

  public List<Map<String, String>> getItems() {
    return items;
  }

  @Override
  public void startElement(String nsURI, String strippedName,
      String tagName, Attributes attributes)
  throws SAXException {

    if (tagName.equalsIgnoreCase("object")) {
      Map<String, String> item = getAttributes(attributes);
      item.put("tag", "object");
      items.add(item);
    } else if (tagName.equalsIgnoreCase("title")) {
      titleMarker = true;
    }

  }

  private Map<String, String> getAttributes(Attributes attributes) {
    Map<String, String> item = new HashMap<String, String>();
    for (int i = 0; i < attributes.getLength(); i++) {
      item.put(attributes.getLocalName(i), attributes.getValue(i));
    }
    return item;
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    if (titleMarker) {
      title = new String(ch, start, length);
      titleMarker = false;
    }
  }
}
