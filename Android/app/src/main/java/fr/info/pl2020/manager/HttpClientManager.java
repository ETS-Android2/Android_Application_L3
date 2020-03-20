package fr.info.pl2020.manager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;
import fr.info.pl2020.BuildConfig;

public class HttpClientManager {

    private static final String BASE_URL = "http://" + BuildConfig.SERVER_HOSTNAME + ':' + BuildConfig.SERVER_PORT;
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String urn, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(BASE_URL + urn, params, responseHandler);
    }

    public static void put(String urn, HttpEntity entity, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(null, BASE_URL + urn, entity, "application/json", responseHandler);
    }

    public static void post(String urn, HttpEntity entity, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(null, BASE_URL + urn, entity, "application/json", responseHandler);
    }
}
