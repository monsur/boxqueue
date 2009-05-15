package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleElement {

  private String name;
  private String value;
  private Map<String, String> attributes;
  private List<SimpleElement> children;

  public SimpleElement() {
    attributes = new HashMap<String, String>();
    children = new ArrayList<SimpleElement>();
  }

  public String getParam(String name) {
    for (SimpleElement child : children) {
      String paramName = child.getAttributes().get("name");
      if (paramName != null && paramName.equals(name)) {
        String paramValue = child.getAttributes().get("value");
        if (paramValue != null) {
          return paramValue;
        }
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  public List<SimpleElement> getChildren() {
    return children;
  }

  private String getString(int tabs, StringBuilder builder) {
    if (builder == null) {
      builder = new StringBuilder();
    }
    builder.append("\r\n");
    for (int i = 0; i < tabs; i++) {
      builder.append("\t");
    }
    tabs++;
    builder.append(name).append(" ");
    for (Entry<String, String> attribute : attributes.entrySet()) {
      builder.append(attribute.getKey() + "=\"" + attribute.getValue() + "\" ");
    }
    for (SimpleElement child : children) {
      child.getString(tabs, builder);
    }
    return builder.toString();
  }

  @Override
  public String toString() {
    return getString(0, null);
  }
}
