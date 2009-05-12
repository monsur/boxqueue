package com.monsur.boxqueue.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelperMethods {

  public static String getFullUrl(String path, UrlWithQuery url) {
    if (path.startsWith("http")) {
      return path;
    } else if (path.startsWith("/")) {
      return (new StringBuilder()).append(url.getProtocol()).append("://")
          .append(url.getAuthority()).append(path).toString();
    } else {
      return (new StringBuilder()).append(url.getProtocol()).append("://")
          .append(url.getAuthority()).append("/").append(url.getPathDirectory()).append(path)
          .toString();
    }
  }

  public static String hash(String stringToHash) throws IOException {
    MessageDigest m;
    try {
      m = MessageDigest.getInstance("MD5");
      m.update(stringToHash.getBytes(), 0, stringToHash.length());
      return convertToHex(m.digest());
    } catch (NoSuchAlgorithmException e) {
      throw new IOException("No such algorithm");
    }
  }

  private static String convertToHex(byte[] data) {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < data.length; i++) {
      int halfbyte = (data[i] >>> 4) & 0x0F;
      int two_halfs = 0;
      do {
        if ((0 <= halfbyte) && (halfbyte <= 9))
              buf.append((char) ('0' + halfbyte));
          else
            buf.append((char) ('a' + (halfbyte - 10)));
        halfbyte = data[i] & 0x0F;
      } while(two_halfs++ < 1);
    }
    return buf.toString();
  }

}
