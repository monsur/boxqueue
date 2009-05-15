package com.monsur.boxqueue.adaptor;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.monsur.boxqueue.adaptor.saxhandler.BoxeeHandler;
import com.monsur.boxqueue.adaptor.saxhandler.GeneralUrlHandler;
import com.monsur.boxqueue.data.ItemSource;
import com.monsur.boxqueue.data.UserItem;
import com.monsur.boxqueue.util.UrlWithQuery;

public class GeneralUrlAdaptor implements VideoAdaptor {

  protected UrlWithQuery url;
  protected BoxeeHandler handler;

  GeneralUrlAdaptor() {
  }

  public ItemSource getItemSource() {
    return handler.getItemSource();
  }

  public String getSourceId() {
    return url.getOriginalUrl();
  }

  public List<UserItem> load() throws AdaptorException {
    try {

      URLConnection urlConnection = getURLConnection(url);
      Parser p = new Parser();
      p.setFeature("http://xml.org/sax/features/namespace-prefixes",true);
      handler.setUrl(url);
      p.setContentHandler((ContentHandler) handler);
      p.parse(new InputSource(urlConnection.getInputStream()));
      return handler.getItems();
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

  public static GeneralUrlAdaptor create(UrlWithQuery url, BoxeeHandler handler) {
    GeneralUrlAdaptor adaptor = new GeneralUrlAdaptor();
    adaptor.url = url;
    if (handler == null) {
      handler = new GeneralUrlHandler();
    }
    adaptor.handler = handler;
    return adaptor;
  }
}
