package fr.info.pl2020.manager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpHeaders;
import fr.info.pl2020.BuildConfig;

public class HttpClientManager {

    private static final String BASE_URL = "http://" + BuildConfig.SERVER_HOSTNAME + ':' + BuildConfig.SERVER_PORT;

    public static void get(String urn, boolean needAuthentication, AsyncHttpResponseHandler responseHandler) {
        createClient(needAuthentication).get(BASE_URL + urn, responseHandler);
    }

    public static void put(String urn, HttpEntity entity, boolean needAuthentication, AsyncHttpResponseHandler responseHandler) {
        createClient(needAuthentication).put(null, BASE_URL + urn, entity, null, responseHandler);
    }

    public static void post(String urn, HttpEntity entity, boolean needAuthentication, AsyncHttpResponseHandler responseHandler) {
        createClient(needAuthentication).post(null, BASE_URL + urn, entity, null, responseHandler);
    }

    private static AsyncHttpClient createClient(boolean needAuthentication) {
        AsyncHttpClient client = new AsyncHttpClient();
        if (needAuthentication) {
            client.addHeader(HttpHeaders.AUTHORIZATION, new AuthenticationManager().getToken());
        }

        client.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        client.addHeader(HttpHeaders.ACCEPT, "application/json");

        return client;
    }
}
