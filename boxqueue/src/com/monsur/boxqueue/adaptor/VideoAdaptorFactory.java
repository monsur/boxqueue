package com.monsur.boxqueue.adaptor;

import com.monsur.boxqueue.adaptor.saxhandler.BoxeeHandler;
import com.monsur.boxqueue.adaptor.saxhandler.VimeoHandler;
import com.monsur.boxqueue.util.UrlWithQuery;

public class VideoAdaptorFactory {

  public static VideoAdaptor create(UrlWithQuery url) {
    // TODO(monsur): Add break.com adaptor
    if (isYouTubeUrl(url)) {
      return YouTubeAdaptor.create(url);
    } else {
      BoxeeHandler handler = null;
      if (isVimeoUrl(url)) {
        handler = new VimeoHandler();
      }
      return GeneralUrlAdaptor.create(url, handler);
    }
  }

  private static boolean isVimeoUrl(UrlWithQuery url) {
    return url.getHost().contains("vimeo.com");
  }

  private static boolean isYouTubeUrl(UrlWithQuery url) {
    return url.getHost().contains("youtube.com");
  }
}
