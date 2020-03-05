package fr.info.pl2020.service;

import com.loopj.android.http.AsyncHttpResponseHandler;

import fr.info.pl2020.manager.HttpClientManager;

public class SemesterServiceBis {

    private final String urn = "/semester";

    public void getAll(AsyncHttpResponseHandler responseHandler) {
        HttpClientManager.get(urn, null, responseHandler);
    }
}
