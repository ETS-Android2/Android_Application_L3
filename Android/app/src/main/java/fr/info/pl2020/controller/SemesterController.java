package fr.info.pl2020.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.adapter.SemesterAdapter;
import fr.info.pl2020.service.SemesterService;

public class SemesterController {

    public void displaySemester(Context context, ExpandableListView expandableListView) {
        new SemesterService().getAll(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == HttpStatus.SC_OK) {
                    Map<String, List<String>> categoryBySemesterMap = new TreeMap<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject semester = response.getJSONObject(i);
                            String semesterId = "Semestre " + semester.getString("id");
                            JSONArray listCategories = semester.getJSONArray("listCat");
                            List<String> categoriesNames = new ArrayList<>();
                            for (int j = 0; j < listCategories.length(); j++) {
                                JSONObject category = listCategories.getJSONObject(j);
                                String categoryName = category.getString("name");
                                categoriesNames.add(categoryName);
                            }
                            categoryBySemesterMap.put(semesterId, categoriesNames);
                        } catch (JSONException e) {
                            Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    SemesterAdapter semesterAdapter = new SemesterAdapter(context, categoryBySemesterMap);
                    expandableListView.setAdapter(semesterAdapter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("SEMESTER", "Echec de la récupération de la liste des semestres (Code: " + statusCode + ")", throwable);
                Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
