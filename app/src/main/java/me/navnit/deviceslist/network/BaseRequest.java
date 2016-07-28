package me.navnit.deviceslist.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dpadmanavaneethan on 7/27/16.
 */
public class BaseRequest {

    Context context;
    protected String baseURL = "https://s3.amazonaws.com/harmony-recruit";
    protected String serviceEndPoint = "";

    protected int error = 4;

    public static final int NO_NETWORK = 1;
    public static final int SERVER_ERROR = 2;
    public static final int INVALID_URL = 3;
    public static final int UNKNOWN_ERROR = 4;
    public static final int NONE = 0;

    public BaseRequest(Context context) {
        this.context = context;
    }

    public String send() {

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // make network call
            try {
                URL url = new URL(baseURL + serviceEndPoint);
                URLConnection urlConn = url.openConnection();

                if (!(urlConn instanceof HttpURLConnection)) {
                    throw new IOException("URL is not an Http URL");
                }
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpConn.getInputStream();
                    String result = convertStreamToString(inputStream);
                    inputStream.close();
                    error = NONE;
                    return result;
                } else {
                    error = SERVER_ERROR;
                }

            } catch (MalformedURLException e) {
                error = INVALID_URL;
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            error = NO_NETWORK;
        }

        return "";
    }

    String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getError() {
        return error;
    }
}
