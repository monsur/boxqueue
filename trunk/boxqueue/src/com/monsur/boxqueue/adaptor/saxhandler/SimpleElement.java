package com.monsur.boxqueue.adaptor.saxhandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleElement {

  private String name;
  private String value;
  private Map<String, String> attributes;
  private List<SimpleElement> children;

  public SimpleElement() {
    attributes = new HashMap<String, String>();
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
}
