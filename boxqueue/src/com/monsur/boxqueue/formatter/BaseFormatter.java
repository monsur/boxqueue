package com.monsur.boxqueue.formatter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monsur.boxqueue.data.UserFeed;
import com.monsur.boxqueue.data.UserItem;

public interface BaseFormatter {
  void getOutput(UserFeed feed, List<UserItem> items,
      HttpServletRequest request, HttpServletResponse response) throws FormatterException;
}
