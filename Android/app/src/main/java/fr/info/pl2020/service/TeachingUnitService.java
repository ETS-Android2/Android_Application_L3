package fr.info.pl2020.service;

import com.loopj.android.http.AsyncHttpResponseHandler;

import fr.info.pl2020.manager.HttpClientManager;

public class TeachingUnitService {

    private final String urn = "/semesterAll"; //TODO

    public void getAll(AsyncHttpResponseHandler responseHandler) {
        HttpClientManager.get(urn, null, responseHandler);
    }
}
