package fr.info.pl2020.service;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;
import fr.info.pl2020.manager.DownloadAndOpenManager;
import fr.info.pl2020.manager.HttpClientManager;
import fr.info.pl2020.model.Career;

public class CareerService {

    private final String urn = "/career";

    private enum GetFilter {
        STUDENT,
        PUBLIC,
        MAIN
    }

    public void getCareerById(int careerId, AsyncHttpResponseHandler responseHandler) {
        if (careerId == 0) {
            getMainCareer(responseHandler);
            return;
        }
        String currentUrn = urn + "/" + careerId;
        HttpClientManager.get(currentUrn, true, responseHandler);
    }

    private void getMainCareer(AsyncHttpResponseHandler responseHandler) {
        getCareer(GetFilter.MAIN, responseHandler);
    }

    public void getAllCareers(AsyncHttpResponseHandler responseHandler) {
        getCareer(GetFilter.STUDENT, responseHandler);
    }

    public void getPublicCareers(AsyncHttpResponseHandler responseHandler) {
        getCareer(GetFilter.PUBLIC, responseHandler);
    }

    private void getCareer(GetFilter filter, AsyncHttpResponseHandler responseHandler) {
        HttpClientManager.get(urn + "?filter=" + filter, true, responseHandler);
    }

    public void createCareer(Career career, AsyncHttpResponseHandler responseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", career.getName());
            jsonObject.put("isPublic", career.isPublicCareer());
            jsonObject.put("mainCareer", career.isMainCareer());
            StringEntity entity = new StringEntity(jsonObject.toString());
            HttpClientManager.post(urn, entity, true, responseHandler);
        } catch (Exception e) {
            Log.e("CAREER_SERVICE", "Echec de la Creation d'un parcours", e);
        }
    }

    public void editCareer(Career career, AsyncHttpResponseHandler responseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", career.getName());
            jsonObject.put("isPublic", career.isPublicCareer());
            jsonObject.put("mainCareer", career.isMainCareer());
            StringEntity entity = new StringEntity(jsonObject.toString());
            HttpClientManager.post(urn + "/" + career.getId(), entity, true, responseHandler);
        } catch (Exception e) {
            Log.e("CAREER_SERVICE", "Echec de la Creation d'un parcours", e);
        }
    }

    public void deleteCareer(Career career, AsyncHttpResponseHandler responseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", career.getName());
            jsonObject.put("isPublic", career.isPublicCareer());
            jsonObject.put("mainCareer", career.isMainCareer());
            StringEntity entity = new StringEntity(jsonObject.toString());
            HttpClientManager.delete(urn + "/" + career.getId(), true, responseHandler);
        } catch (Exception e) {
            Log.e("CAREER_SERVICE", "Echec de la Suppression d'un parcours", e);
        }
    }

    public void saveCareer(int careerId, List<Integer> teachingUnitIdList, AsyncHttpResponseHandler responseHandler) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Integer id : teachingUnitIdList) {
                JSONObject tu = new JSONObject();
                tu.put("id", id);
                jsonArray.put(tu);
            }
            Log.d("TEST", jsonArray.toString());
            StringEntity entity = new StringEntity(jsonArray.toString());
            HttpClientManager.put(urn + "/" + careerId, entity, true, responseHandler);
        } catch (UnsupportedEncodingException e) {
            Log.e("CAREER_SERVICE", "Echec de la conversion de la liste des UE en StringEntity", e);
        } catch (JSONException e) {
            Log.e("CAREER_SERVICE", "Echec de la conversion de la liste des UE en JSONObject", e);
        }
    }

    public void exportCareer(Context context, int careerId, Career.ExportFormat format) {
        new DownloadAndOpenManager().downloadFile(context, urn + "/" + careerId + "/export?format=" + format, "Mon parcours", format);
    }

    public void sendCareer(int careerId, AsyncHttpResponseHandler responseHandler) {
        HttpClientManager.get(urn + "/" + careerId + "/send", true, responseHandler);
    }
}
