package fr.info.pl2020.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import fr.info.pl2020.service.CareerService;

public class CareerController {

    public void saveCareer(Context context, List<Integer> teachingUnitIdList) {
        new CareerService().saveCareer(teachingUnitIdList, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                if (response.has("error")) {
                    alertDialog.setTitle("Erreur");
                    alertDialog.setMessage(response.optString("error"));
                } else {
                    alertDialog.setMessage("Le parcours a bien été enregistré.");
                }
                alertDialog.setNeutralButton("OK", (dialog, which) -> {
                });
                alertDialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("CAREER", "Echec", throwable);
                Toast.makeText(context, "echec", Toast.LENGTH_SHORT);
            }
        });
    }
}
