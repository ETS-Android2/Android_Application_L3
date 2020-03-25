package fr.info.pl2020.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.adapter.SemesterAdapter;
import fr.info.pl2020.model.Semester;
import fr.info.pl2020.service.SemesterService;

import static fr.info.pl2020.manager.AuthenticationManager.callLoginActivity;

public class SemestersListController {
    public void displaySemesterList(Context context, ListView listView) {
        new SemesterService().getAll(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == HttpStatus.SC_OK) {
                    List<Semester> semestersList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonSemester = response.getJSONObject(i);
                            Semester semester = new Semester(jsonSemester.getInt("id"));
                            semestersList.add(semester);
                        } catch (JSONException e) {
                            Log.e("SEMESTER_SERVICE", "Erreur lors de la récupération des informations d'un semestre", e);
                            Toast.makeText(context, R.string.standard_exception, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    semestersList.sort((a, b) -> a.getId() - b.getId());

                    SemesterAdapter categoryAdapter = new SemesterAdapter(context, semestersList);
                    listView.setAdapter(categoryAdapter);
                } else {
                    Log.e("SEMESTER", "Statut HTTP de la réponse inattendu (Code: " + statusCode + ")");
                    Toast.makeText(context, R.string.unexpected_http_status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    callLoginActivity(context);
                } else {
                    Log.e("SEMESTER", "Echec de la récupération de la liste des semestres (Code: " + statusCode + ")", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
