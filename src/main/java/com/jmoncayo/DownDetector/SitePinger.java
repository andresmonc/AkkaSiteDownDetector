package com.jmoncayo.DownDetector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public class SitePinger {

    private URI uri;

    public SitePinger(URI uri) {
        this.uri = uri;
    }

    public int pingSite() throws IOException {
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection httpURLConnection;
        httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
        HostnameVerifier allHostsValid = (hostName,session) ->true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        httpURLConnection.setConnectTimeout(500);
        httpURLConnection.setRequestMethod("HEAD");
        httpURLConnection.setRequestProperty("Connection","keep-alive");
        return httpURLConnection.getResponseCode();
    }

}
