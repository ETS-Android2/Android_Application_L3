package fr.info.pl2020.service;

import com.loopj.android.http.AsyncHttpResponseHandler;

import fr.info.pl2020.manager.HttpClientManager;

public class TeachingUnitService {

    private final String urn = "/teachingUnit";

    public void getAllBySemester(int semesterId, AsyncHttpResponseHandler responseHandler) {
        String currentUrn = this.urn + "?showUserSelection=true&semester=" + semesterId;
        HttpClientManager.get(currentUrn, true, responseHandler);
    }

    public void getOne(int teachingUnitId, AsyncHttpResponseHandler responseHandler) {
        HttpClientManager.get(this.urn + "/" + teachingUnitId, true, responseHandler);
    }
}
