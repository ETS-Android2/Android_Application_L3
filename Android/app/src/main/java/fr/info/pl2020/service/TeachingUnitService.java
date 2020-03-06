package fr.info.pl2020.service;

import com.loopj.android.http.AsyncHttpResponseHandler;

import fr.info.pl2020.manager.HttpClientManager;

public class TeachingUnitService {

    private final String urn = "/teachingUnit";

    public void getAllBySemester(int semesterId, AsyncHttpResponseHandler responseHandler) {
        String currentUrn = this.urn + "?semester=" + semesterId;
        HttpClientManager.get(currentUrn, null, responseHandler);
    }
}
