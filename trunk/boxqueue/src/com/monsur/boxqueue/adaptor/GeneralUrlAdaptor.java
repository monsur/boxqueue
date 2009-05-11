package com.monsur.boxqueue.adaptor;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.UrlWithQuery;

public class GeneralUrlAdaptor implements VideoAdaptor {

  private UrlWithQuery url;

  GeneralUrlAdaptor() {
  }

  public ItemSource getItemSource() {
    return ItemSource.GENERAL;
  }

  public String getSourceId() {
    return url.getOriginalUrl();
  }

  public List<UserItem> load() throws AdaptorException {
    List<UserItem> items = new ArrayList<UserItem>();

    try {

      URLConnection urlConnection = getURLConnection(url);
      Parser p = new Parser();
      p.setFeature("http://xml.org/sax/features/namespace-prefixes",true);
      p.setContentHandler(new VideoSaxHandler());
      p.parse(new InputSource(urlConnection.getInputStream()));

      int count = 0;
      String title = ((VideoSaxHandler) p.getContentHandler()).getTitle();
      for (Map<String, String> tagItem : ((VideoSaxHandler) p.getContentHandler()).getItems()) {
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
        item.getMediaContent().setUrl(data);
        items.add(item);
        count++;
      }
    } catch (IOException e) {
      throw new AdaptorException(e);
    } catch (SAXNotRecognizedException e) {
      throw new AdaptorException(e);
    } catch (SAXNotSupportedException e) {
      throw new AdaptorException(e);
    } catch (SAXException e) {
      throw new AdaptorException(e);
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
}
