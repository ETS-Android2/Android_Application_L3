package fr.info.pl2020.controller;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.adapter.SearchAdapter;
import fr.info.pl2020.manager.AuthenticationManager;
import fr.info.pl2020.model.TeachingUnitListContent.TeachingUnit;
import fr.info.pl2020.service.TeachingUnitService;
import fr.info.pl2020.util.JsonModelConvert;

public class SearchController {

    private TeachingUnitService teachingUnitService = new TeachingUnitService();

    public void searchTeachingUnit(Activity context, String name, int semesterId) {
        this.teachingUnitService.getAll(semesterId, name, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == HttpStatus.SC_OK) {
                    List<TeachingUnit> teachingUnitList = JsonModelConvert.jsonArrayToTeachingUnits(response);

                    if (teachingUnitList.isEmpty()) {
                        TextView searchErrorTextView = context.findViewById(R.id.searchErrorTextView);
                        searchErrorTextView.setText(R.string.search_no_teachingunit_found);
                        searchErrorTextView.setVisibility(View.VISIBLE);
                    }

                    SearchAdapter searchAdapter = new SearchAdapter(context, teachingUnitList, semesterId == 0);
                    ListView listView = context.findViewById(R.id.searchListView);
                    listView.setAdapter(searchAdapter);
                } else {
                    Log.e("SEARCH", "Statut HTTP de la réponse inattendu (Code: " + statusCode + ")");
                    Toast.makeText(context, R.string.unexpected_http_status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else {
                    Log.e("SEARCH", "Echec de la recherche de l'UE '" + name + "' (Code: " + statusCode + ")", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
