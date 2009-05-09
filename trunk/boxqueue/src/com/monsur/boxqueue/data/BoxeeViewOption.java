package com.monsur.boxqueue.data;

public enum BoxeeViewOption {

  LINE("line", 1),
  THUMB("thumb", 2),
  THUMB_WITH_PREVIEW("thumb-with-preview", 3),
  DETAILED_LIST("detailed-list", 4);

  private final String viewType;
  private final int id;

  BoxeeViewOption(String viewType, int id) {
    this.viewType = viewType;
    this.id = id;
  }

  public String getViewType() {
    return viewType;
  }

  public int getId() {
    return id;
  }

  public static BoxeeViewOption getById(int id) {
    for (BoxeeViewOption bvo : BoxeeViewOption.values()) {
      if (bvo.getId() == id) {
        return bvo;
      }
    }
    throw new IllegalArgumentException("Invalid ID " + id);
  }
}
