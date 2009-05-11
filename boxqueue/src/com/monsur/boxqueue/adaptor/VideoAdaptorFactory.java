package com.monsur.boxqueue.adaptor;

import com.monsur.boxqueue.util.UrlWithQuery;

public class VideoAdaptorFactory {

  public static VideoAdaptor create(UrlWithQuery url) {
    // TODO(monsur): Add vimeo adaptor
    // TODO(monsur): Add break.com adaptor
    if (YouTubeAdaptor.validUrl(url)) {
      return YouTubeAdaptor.create(url);
    }
    return GeneralUrlAdaptor.create(url);
  }
}
