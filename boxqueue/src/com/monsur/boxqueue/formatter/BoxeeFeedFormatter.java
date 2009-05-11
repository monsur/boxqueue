package com.monsur.boxqueue.formatter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.monsur.boxqueue.data.BoxeeViewOption;
import com.monsur.boxqueue.data.MediaContent;
import com.monsur.boxqueue.data.MediaKeywords;
import com.monsur.boxqueue.data.MediaThumbnail;
import com.monsur.boxqueue.data.UserFeed;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.servlet.ServletHelper;

// TODO(monsur): support etags?
public class BoxeeFeedFormatter implements BaseFormatter {

  private static SimpleDateFormat RFC822DATEFORMAT
      = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

  public void getOutput(UserFeed feed, List<UserItem> items, HttpServletRequest request, HttpServletResponse response) throws FormatterException {
    try {
      PrintWriter out = response.getWriter();
      StreamResult streamResult = new StreamResult(out);
      SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
      TransformerHandler hd = null;
      hd = tf.newTransformerHandler();
      Transformer serializer = hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");
      hd.setResult(streamResult);
      hd.startDocument();
      Map<String, String> attributesMap = new HashMap<String, String>();
      attributesMap.put("version", "2.0");
      attributesMap.put("xmlns:media", "http://search.yahoo.com/mrss/");
      attributesMap.put("xmlns:atom", "http://www.w3.org/2005/Atom");
      attributesMap.put("xmlns:boxee", "http://boxee.tv/spec/rss/");
      startElement(hd, "rss", attributesMap);
      startElement(hd, "channel");
      addElement(hd, "title", null, feed.getTitle());
      addElement(hd, "description", null, feed.getTitle());
      addFeedLinks(hd, feed, request);
      addBoxeeDisplay(hd, feed);
      // TODO(monsur): Add boxee:expiry
      // TODO(monsur): Add boxee:background-image
      for (UserItem item : items) {
        addItem(hd, item, request);
      }
      endElement(hd, "channel");
      endElement(hd, "rss");
      hd.endDocument();
    } catch (IOException ex) {
      throw new FormatterException(ex);
    } catch (TransformerConfigurationException ex) {
      throw new FormatterException(ex);
    } catch (SAXException ex) {
      throw new FormatterException(ex);
    }
  }

  private void addFeedLinks(TransformerHandler hd, UserFeed feed, HttpServletRequest request)
      throws SAXException, UnsupportedEncodingException {
    String link = ServletHelper.getUrl("/feed/" + URLEncoder.encode(feed.getPath(), "UTF-8"), request);
    addElement(hd, "link", null, link);
    Map<String, String> attributesMap = new HashMap<String, String>();
    attributesMap.put("href", link);
    attributesMap.put("rel", "self");
    attributesMap.put("type", "application/rss+xml");
    addElement(hd, "atom:link", attributesMap, null);
  }

  private String getDateAsRFC822String(Date date) {
    return RFC822DATEFORMAT.format(date);
  }

  private void addItem(TransformerHandler hd, UserItem item, HttpServletRequest request) throws SAXException, UnsupportedEncodingException {
    startElement(hd, "item");
    addElement(hd, "pubDate", null, getDateAsRFC822String(item.getDateSort()));
    addElement(hd, "title", null, item.getTitle());
    addElement(hd, "description", null, item.getDescription());
    Map<String, String> attributesMap = new HashMap<String, String>();
    attributesMap.put("isPermaLink", "false");
    addElement(hd, "guid", attributesMap, item.getGuid());
    // TODO(monsur): add link
    addMediaContent(hd, item, request, item.getMediaContent());
    addMediaThumbnail(hd, item.getMediaThumbnail());
    // TODO(monsur): add media credit
    // TODO(monsur): add media rating
    addMediaKeywords(hd, item.getMediaKeywords());
    // TODO(monsur): add media category
    // TODO(monsur): add boxee:alternative-link
    endElement(hd, "item");
  }

  private void addMediaKeywords(TransformerHandler hd,
      MediaKeywords mediaKeywords) throws SAXException {
    if (mediaKeywords.exists()) {
      addElement(hd, "media:keywords", null, mediaKeywords.getValue());
    }
  }

  private void addMediaThumbnail(TransformerHandler hd,
      MediaThumbnail mediaThumbnail) throws SAXException {
    if (mediaThumbnail.exists()) {
      Map<String, String> attributesMap = new HashMap<String, String>();
      attributesMap.put("url", mediaThumbnail.getUrl());
      addElement(hd, "media:thumbnail", attributesMap, null);
    }
  }

  private void addMediaContent(TransformerHandler hd, UserItem item, HttpServletRequest request, MediaContent mediaContent) throws SAXException, UnsupportedEncodingException {
    Map<String, String> attributesMap = new HashMap<String, String>();
    attributesMap.put("url", ServletHelper.getUrl("/go?guid=" + URLEncoder.encode(item.getGuid(), "UTF-8"), request));
    attributesMap.put("type", mediaContent.getType());
    // TODO(monsur): Duration is appearing as minutes
    if (mediaContent.getDuration() != null) {
      attributesMap.put("duration", Integer.toString(mediaContent.getDuration()));
    }
    addElement(hd, "media:content", attributesMap, null);
  }

  private void addBoxeeDisplay(TransformerHandler hd, UserFeed feed) throws SAXException {
    startElement(hd, "boxee:display");
    addBoxeeView(hd, feed);
    // TODO(monsur): Add boxee sort
    endElement(hd, "boxee:display");
  }

  private void addBoxeeView(TransformerHandler hd, UserFeed feed) throws SAXException {
    Map<String, String> attributesMap = new HashMap<String, String>();
    attributesMap.put("default", Integer.toString(feed.getBoxeeViewOption().getId()));
    startElement(hd, "boxee:view", attributesMap);
    for (BoxeeViewOption bvo : BoxeeViewOption.values()) {
      attributesMap.clear();
      attributesMap.put("id", Integer.toString(bvo.getId()));
      attributesMap.put("view-type", bvo.getViewType());
      addElement(hd, "boxee:view-option", attributesMap, null);
    }
    endElement(hd, "boxee:view");
  }

  private void addElement(TransformerHandler hd, String elementName,
      Map<String, String> attributesMap, String elementValue) throws SAXException {
    startElement(hd, elementName, attributesMap);
    if (elementValue != null) {
      setText(hd, elementValue);
    }
    endElement(hd, elementName);
  }

  private void setText(TransformerHandler hd, String text) throws SAXException {
    hd.characters(text.toCharArray(), 0, text.length());
  }

  private void startElement(TransformerHandler hd, String elementName) throws SAXException {
    startElement(hd, elementName, null);
  }

  private void startElement(TransformerHandler hd, String elementName, Map<String, String> attributesMap) throws SAXException {
    AttributesImpl attributes = new AttributesImpl();
    if (attributesMap != null) {
      Iterator it = attributesMap.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
        attributes.addAttribute("", "", pairs.getKey(), "", pairs.getValue());
      }
    }
    hd.startElement("", "", elementName, attributes);
  }

  private void endElement(TransformerHandler hd, String elementName) throws SAXException {
    hd.endElement("", "", elementName);
  }
}
