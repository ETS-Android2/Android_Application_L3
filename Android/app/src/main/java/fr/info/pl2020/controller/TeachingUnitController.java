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
import fr.info.pl2020.adapter.TeachingUnitAdapter;
import fr.info.pl2020.model.TeachingUnit;
import fr.info.pl2020.service.TeachingUnitService;

public class TeachingUnitController {

    public void displayTeachingUnits(Context context, ExpandableListView expandableListView, int semesterId) {
        new TeachingUnitService().getAllBySemester(semesterId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == HttpStatus.SC_OK) {
                    Map<String, List<TeachingUnit>> teachingUnitByCategoryMap = new TreeMap<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonTeachingUnit = response.getJSONObject(i);
                            int teachingUnitId = jsonTeachingUnit.getInt("id");
                            String teachingUnitName = jsonTeachingUnit.getString("name");

                            TeachingUnit teachingUnit = new TeachingUnit(teachingUnitId, teachingUnitName);
                            if (jsonTeachingUnit.optBoolean("selectedByStudent")) {
                                teachingUnit.setSelectedByStudent(true);
                            }
                            String categoryName = jsonTeachingUnit.getJSONObject("category").getString("name");
                            teachingUnitByCategoryMap.putIfAbsent(categoryName, new ArrayList<>());
                            teachingUnitByCategoryMap.get(categoryName).add(teachingUnit);
                        } catch (JSONException e) {
                            Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    for (List<TeachingUnit> list : teachingUnitByCategoryMap.values()) {
                        list.sort((o1, o2) -> o1.getId() - o2.getId());
                    }

                    TeachingUnitAdapter categoryAdapter = new TeachingUnitAdapter(context, teachingUnitByCategoryMap);
                    expandableListView.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("CATEGORY", "Echec de la récupération de la liste des categories (Code: " + statusCode + ")", throwable);
                Toast.makeText(context, "La connexion avec le serveur a échoué", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
