package fr.info.pl2020.service;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;
import fr.info.pl2020.manager.HttpClientManager;

public class CareerService {

    private final String urn = "/student/career";

    public void getCareer(int semester, AsyncHttpResponseHandler responseHandler) {
        String currentUrn = urn + (semester == 0 ? "" : "?semester=" + semester);
        HttpClientManager.get(currentUrn, true, responseHandler);
    }

    public void saveCareer(List<Integer> teachingUnitIdList, int semester,AsyncHttpResponseHandler responseHandler) {
        JSONArray jsonArray = new JSONArray();
        for (Integer id : teachingUnitIdList) {
            jsonArray.put(id);
        }

        try {
            StringEntity entity = new StringEntity(jsonArray.toString());
            HttpClientManager.put(urn+ (semester == 0 ? "" : "?semester=" + semester), entity, true, responseHandler);
        } catch (UnsupportedEncodingException e) {
            Log.e("CAREER_SERVICE", "Echec de la conversion de la liste des UE en StringEntity", e);
        }
    }
}
