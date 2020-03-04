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
import fr.info.pl2020.service.TeachingUnitService;

public class TeachingUnitController {

    public void displayTeachingUnits(Context context, ExpandableListView expandableListView) {
        new TeachingUnitService().getAll(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == HttpStatus.SC_OK) {
                    Map<String, List<String>> teachingUnitByCategoryMap = new TreeMap<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject category = response.getJSONObject(i);
                            String categoryId = "Category " + category.getString("id");
                            JSONArray listCategories = category.getJSONArray("listCat");
                            List<String> categoriesNames = new ArrayList<>();
                            for (int j = 0; j < listCategories.length(); j++) {
                                JSONObject teachingUnit = listCategories.getJSONObject(j);
                                String teachingUnitName = teachingUnit.getString("name");
                                categoriesNames.add(teachingUnitName);
                            }
                            teachingUnitByCategoryMap.put(categoryId, categoriesNames);
                        } catch (JSONException e) {
                            Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    TeachingUnitAdapter categoryAdapter = new TeachingUnitAdapter(context, teachingUnitByCategoryMap);
                    expandableListView.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("CATEGORY", "Echec de la récupération de la liste des categories (Code: " + statusCode + ")", throwable);
                Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
