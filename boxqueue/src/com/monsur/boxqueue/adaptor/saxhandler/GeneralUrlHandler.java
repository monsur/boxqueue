package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.HelperMethods;

public class GeneralUrlHandler extends BaseUrlHandler implements BoxeeHandler {

  private boolean titleMarker = false;
  private Stack<SimpleElement> stack;
  private String title = "";
  private List<SimpleElement> elements;

  public GeneralUrlHandler() {
    stack = new Stack<SimpleElement>();
  }

  @Override
  public void startElement(String nsURI, String strippedName, String tagName,
      Attributes attributes) throws SAXException {
    // TODO(monsur): Add support for params inside of object tags
    // TODO(monsur): Add support for the embed tag
    if (tagName.equalsIgnoreCase("object")) {
      SimpleElement element = new SimpleElement();
      element.setName("object");
      element.setAttributes(getAttributes(attributes));
      stack.add(element);
    } else if (tagName.equalsIgnoreCase("title")) {
      titleMarker = true;
    }
  }

  @Override
  public void endElement( String uri, String localName, String tagName ) {
    SimpleElement tmp = stack.pop();
    if (tagName.equalsIgnoreCase("object")) {
      elements.add(tmp);
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

  @Override
  public void endDocument() {
    items = new ArrayList<UserItem>();
    int count = 0;
    for (SimpleElement element : elements) {
      UserItem item = new UserItem();
      String type = element.getAttributes().get("type");
      if (type == null || !type.equals("application/x-shockwave-flash")) {
        continue;
      }
      String data = element.getAttributes().get("data");
      if (data == null) {
        continue;
      }
      if (count == 0) {
        item.setTitle(title);
      } else {
        item.setTitle(title + " (" + count + ")");
      }
      item.getMediaContent().setType(type);
      item.getMediaContent().setUrl(HelperMethods.getFullUrl(data, url));
      items.add(item);
      count++;
    }
  }
}
