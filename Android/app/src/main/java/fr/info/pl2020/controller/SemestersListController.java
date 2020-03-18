package fr.info.pl2020.controller;

import android.content.Context;
import android.content.Intent;
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
import fr.info.pl2020.activity.LoginActivity;
import fr.info.pl2020.adapter.SemesterAdapter;
import fr.info.pl2020.model.Semester;
import fr.info.pl2020.service.SemesterService;

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
                           Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                           return;
                       }
                   }
                   semestersList.sort((a, b) -> a.getId() - b.getId());

                   SemesterAdapter categoryAdapter = new SemesterAdapter(context, semestersList);
                   listView.setAdapter(categoryAdapter);
               }
           }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if (statusCode == 401) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
                    else {
                    Log.e("SEMESTER", "Echec de la récupération de la liste des semestres (Code: " + statusCode + ")", throwable);
                    Toast.makeText(context, "La connexion avec le serveur a échoué", Toast.LENGTH_SHORT).show();
                }

            }
        }
        );
    }
}
