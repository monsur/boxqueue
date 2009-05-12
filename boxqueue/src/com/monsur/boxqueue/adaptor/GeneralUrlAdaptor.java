package com.monsur.boxqueue.adaptor;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.HelperMethods;
import com.monsur.boxqueue.util.UrlWithQuery;

public class GeneralUrlAdaptor implements VideoAdaptor {

  protected UrlWithQuery url;

  GeneralUrlAdaptor() {
  }

  public ItemSource getItemSource() {
    return ItemSource.GENERAL;
  }

  public String getSourceId() {
    return url.getOriginalUrl();
  }

  protected DefaultHandler getSaxHandler() {
    return new GeneralUrlSaxHandler();
  }

  public List<UserItem> load() throws AdaptorException {
    try {

      URLConnection urlConnection = getURLConnection(url);
      Parser p = new Parser();
      p.setFeature("http://xml.org/sax/features/namespace-prefixes",true);
      p.setContentHandler(getSaxHandler());
      p.parse(new InputSource(urlConnection.getInputStream()));
      return getItemsFromHtml(p.getContentHandler());
    } catch (IOException e) {
      throw new AdaptorException(e);
    } catch (SAXNotRecognizedException e) {
      throw new AdaptorException(e);
    } catch (SAXNotSupportedException e) {
      throw new AdaptorException(e);
    } catch (SAXException e) {
      throw new AdaptorException(e);
    }
  }

  private List<UserItem> getItemsFromHtml(ContentHandler contentHandler) {
    List<UserItem> items = new ArrayList<UserItem>();
    GeneralUrlSaxHandler saxHandler = (GeneralUrlSaxHandler) contentHandler;
    int count = 0;
    String title = saxHandler.getTitle();
    for (Map<String, String> tagItem : saxHandler.getItems()) {
      UserItem item = new UserItem();
      String type = tagItem.get("type");
      if (type == null || !type.equals("application/x-shockwave-flash")) {
        continue;
      }
      String data = tagItem.get("data");
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
    return items;
  }

  private URLConnection getURLConnection(UrlWithQuery url) throws IOException {
    URLConnection urlConnection = url.getJavaNetUrl().openConnection();
    urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    urlConnection.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
    urlConnection.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
    urlConnection.setRequestProperty("Cache-Control", "no");
    urlConnection.setRequestProperty("Referer", "http://localhost:8080/");
    urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.10) Gecko/2009042315 Firefox/3.0.10,gzip(gfe)");
    return urlConnection;
  }

  public static GeneralUrlAdaptor create(UrlWithQuery url) {
    GeneralUrlAdaptor adaptor = new GeneralUrlAdaptor();
    adaptor.url = url;
    return adaptor;
  }

  public class GeneralUrlSaxHandler extends DefaultHandler {

    private boolean titleMarker = false;

    private String title = "";
    private List<Map<String, String>> items;

    public GeneralUrlSaxHandler() {
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

      // TODO(monsur): Add support for params inside of object tags
      // TODO(monsur): Add support for the embed tag
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

}
