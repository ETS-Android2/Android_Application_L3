package fr.info.pl2020.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.activity.TeachingUnitListActivity;
import fr.info.pl2020.adapter.TeachingUnitAdapter;
import fr.info.pl2020.model.TeachingUnitListContent;
import fr.info.pl2020.model.TeachingUnitListContent.TeachingUnit;
import fr.info.pl2020.service.TeachingUnitService;
import fr.info.pl2020.util.FunctionsUtils;
import fr.info.pl2020.util.JsonModelConvert;

public class TeachingUnitController {

    private CareerController careerController = new CareerController();

    public void updateTeachingUnits(Context context, int semesterId) {
        new TeachingUnitService().getAll(semesterId, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode != HttpStatus.SC_OK) {
                    Log.e("TEACHING_UNIT", "Statut HTTP de la réponse inattendu (Code: " + statusCode + ")");
                    Toast.makeText(context, R.string.standard_exception, Toast.LENGTH_SHORT).show();
                }

                List<TeachingUnit> teachingUnits = JsonModelConvert.jsonArrayToTeachingUnits(response);
                for (TeachingUnit tu : teachingUnits) {
                    TeachingUnitListContent.addItem(tu);
                }

                careerController.getCareer(context, semesterId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
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
