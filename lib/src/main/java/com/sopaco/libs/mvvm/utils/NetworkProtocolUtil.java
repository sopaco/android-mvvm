package com.sopaco.libs.mvvm.utils;

/**
 * Created by meng.jiang on 2015/2/12.
 */
public class NetworkProtocolUtil {
    public static boolean isNetworkResource(String uri) {
        uri = uri.toLowerCase();
        return uri.startsWith("http://") || uri.startsWith("https://");
    }
}
