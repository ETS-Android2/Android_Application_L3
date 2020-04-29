package fr.info.pl2020.service;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;
import fr.info.pl2020.manager.DownloadAndOpenManager;
import fr.info.pl2020.manager.HttpClientManager;
import fr.info.pl2020.model.Career;

public class CareerService {

    private final String urn = "/career";
    private final String urnMain = "/career/main";
    private final String urnStudent = "/career/student";

    public void getCareer(int semester, AsyncHttpResponseHandler responseHandler) {
        String currentUrn = urnMain + (semester == 0 ? "" : "?semester=" + semester);
        HttpClientManager.get(currentUrn, true, responseHandler);
    }

    public void saveCareer(List<Integer> teachingUnitIdList, int semester, AsyncHttpResponseHandler responseHandler) {
        JSONArray jsonArray = new JSONArray();
        for (Integer id : teachingUnitIdList) {
            jsonArray.put(id);
        }

        try {
            StringEntity entity = new StringEntity(jsonArray.toString());
            HttpClientManager.put(urnMain + (semester == 0 ? "" : "?semester=" + semester), entity, true, responseHandler);
        } catch (UnsupportedEncodingException e) {
            Log.e("CAREER_SERVICE", "Echec de la conversion de la liste des UE en StringEntity", e);
        }
    }

    public void getAllCareer(AsyncHttpResponseHandler responseHandler) {
        HttpClientManager.get(urnStudent, true, responseHandler);
    }

    public void exportCareer(Context context, int careerId, Career.ExportFormat format) {
        new DownloadAndOpenManager().downloadFile(context, urn + "/" + careerId + "/export?format=" + format, "Mon parcours", format);
    }

    public void exportCareer(int careerId, Career.ExportFormat format, AsyncHttpResponseHandler responseHandler) {
        String currentUrn = urn + "/" + careerId + "/export?format=" + format;
        HttpClientManager.get(currentUrn, true, responseHandler);
    }
}
