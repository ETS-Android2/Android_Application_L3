package fr.info.pl2020.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.activity.TeachingUnitListActivity;
import fr.info.pl2020.adapter.TeachingUnitAdapter;
import fr.info.pl2020.manager.AuthenticationManager;
import fr.info.pl2020.model.TeachingUnitListContent;
import fr.info.pl2020.model.TeachingUnitListContent.TeachingUnit;
import fr.info.pl2020.service.TeachingUnitService;
import fr.info.pl2020.util.FunctionsUtils;

public class TeachingUnitController {

    private CareerController careerController = new CareerController();

    public void updateTeachingUnits(Context context, int semesterId) {
        if (!TeachingUnitListContent.TEACHING_UNITS.isEmpty()) {
            ExpandableListView expandableListView = ((Activity) context).findViewById(R.id.teachingunit_list);
            ((TeachingUnitAdapter) expandableListView.getExpandableListAdapter()).notifyDataSetChanged();
            return;
        }

        new TeachingUnitService().getAllBySemester(semesterId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode != HttpStatus.SC_OK) {
                    Log.e("TEACHING_UNIT", "Statut HTTP de la réponse inattendu (Code: (Code: " + statusCode + ")");
                    Toast.makeText(context, R.string.standard_exception, Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonTeachingUnit = response.getJSONObject(i);

                        int teachingUnitId = jsonTeachingUnit.getInt("id");
                        String teachingUnitName = jsonTeachingUnit.getString("name");
                        String teachingUnitCode = jsonTeachingUnit.getString("code");
                        String teachingUnitDescription = jsonTeachingUnit.getString("description");
                        int teachingUnitSemester = jsonTeachingUnit.getInt("semester");
                        String teachingUnitCategory = jsonTeachingUnit.getString("category");

                        TeachingUnit tu = new TeachingUnit(teachingUnitId, teachingUnitName, teachingUnitCode, teachingUnitDescription, teachingUnitSemester, teachingUnitCategory);
                        TeachingUnitListContent.addItem(tu);
                    } catch (JSONException e) {
                        Log.e("TEACHING_UNIT", "Erreur lors de la récupération des informations d'une UE", e);
                        Toast.makeText(context, R.string.standard_exception, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                careerController.getCareer(context, semesterId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void displayTeachingUnitDetails(Context context, TextView nameTextView, TextView codeTextView, TextView descriptionTextView, int teachingUnitId) {
        new TeachingUnitService().getOne(teachingUnitId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == HttpStatus.SC_OK) {
                    try {
                        nameTextView.setText(response.getString("name"));
                        codeTextView.setText(response.getString("code"));
                        descriptionTextView.setText(response.getString("description"));
                    } catch (JSONException e) {
                        Log.e("TEACHING_UNIT", "Echec de la récupération des informations de l'UE '" + teachingUnitId + "' depuis le JSON", e);
                    }
                } else {
                    Log.e("TEACHING_UNIT", "Statut HTTP de la réponse inattendu (Code: " + statusCode + ")");
                    Toast.makeText(context, R.string.unexpected_http_status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else {
                    Log.e("TEACHING_UNIT", "Echec de la récupération de la description de l'UE '" + teachingUnitId + "' (Code: " + statusCode + ")", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setupExpandableListView(Context context) {
        ExpandableListView expandableListView = ((Activity) context).findViewById(R.id.teachingunit_list);
        expandableListView.setAdapter(new TeachingUnitAdapter(context, TeachingUnitListContent.getTeachingUnitByCategory(), TeachingUnitListActivity.isTwoPane));
        int lastOpenedTU = TeachingUnitListContent.getLastOpenedTeachingUnit();
        if (lastOpenedTU != 0 && TeachingUnitListContent.TEACHING_UNITS.containsKey(lastOpenedTU)) {
            TeachingUnit tu = TeachingUnitListContent.TEACHING_UNITS.get(lastOpenedTU);
            if (tu != null) {
                int index = FunctionsUtils.getIndex(TeachingUnitListContent.getTeachingUnitByCategory().keySet(), tu.getCategory());
                expandableListView.expandGroup(index);
            }
        }
    }
}
