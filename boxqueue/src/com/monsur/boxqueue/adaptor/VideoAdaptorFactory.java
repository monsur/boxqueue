package com.monsur.boxqueue.adaptor;

import com.monsur.boxqueue.util.UrlWithQuery;

public class VideoAdaptorFactory {

  public static VideoAdaptor create(UrlWithQuery url) {
    // TODO(monsur): Add vimeo adaptor
    if (YouTubeAdaptor.validUrl(url)) {
      return YouTubeAdaptor.create(url);
    }
    return null;
  }
}
