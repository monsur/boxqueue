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
    elements = new ArrayList<SimpleElement>();
  }

  @Override
  public void startElement(String nsURI, String strippedName, String tagName,
      Attributes attributes) throws SAXException {
    // TODO(monsur): Add support for params inside of object tags
    if (tagName.equalsIgnoreCase("object")) {
      SimpleElement element = new SimpleElement();
      element.setName("object");
      element.setAttributes(getAttributes(attributes));
      stack.add(element);
    } else if (tagName.equalsIgnoreCase("embed")) {
      SimpleElement element = new SimpleElement();
      element.setName("embed");
      element.setAttributes(getAttributes(attributes));
      stack.push(element);
    } else if (tagName.equalsIgnoreCase("title")) {
      titleMarker = true;
    }
  }

  @Override
  public void endElement( String uri, String localName, String tagName ) {
    if (tagName.equalsIgnoreCase("object") || tagName.equalsIgnoreCase("embed")) {
      elements.add(stack.pop());
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
    while (stack.size() > 0) {
      SimpleElement tmp = stack.pop();
      if (tmp.getName().equals("object") || tmp.getName().equals("embed")) {
        elements.add(tmp);
      }
    }
    items = new ArrayList<UserItem>();
    for (SimpleElement element : elements) {
      UserItem item = null;
      if (element.getName().equals("object")) {
        item = getUserItemFromObject(element);
      } else if (element.getName().equals("embed")) {
        item = getUserItemFromEmbed(element);
      }
      if (item != null) {
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
  }

  private UserItem getUserItemFromEmbed(SimpleElement element) {
    String type = element.getAttributes().get("type");
    if (type == null || !type.equals("application/x-shockwave-flash")) {
      return null;
    }
    String src = element.getAttributes().get("src");
    if (src == null) {
      return null;
    }
    UserItem item = new UserItem();
    item.getMediaContent().setType(type);
    item.getMediaContent().setUrl(HelperMethods.getFullUrl(src, url));
    return item;
  }

  private UserItem getUserItemFromObject(SimpleElement element) {
    String type = element.getAttributes().get("type");
    if (type == null || !type.equals("application/x-shockwave-flash")) {
      return null;
    }
    String data = element.getAttributes().get("data");
    if (data == null) {
      return null;
    }
    UserItem item = new UserItem();
    item.getMediaContent().setType(type);
    item.getMediaContent().setUrl(HelperMethods.getFullUrl(data, url));
    return item;
  }
}
