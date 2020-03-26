package fr.info.pl2020.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.manager.AuthenticationManager;
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
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else if (statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    Log.d("TEST", errorResponse.toString());
                    alertDialog.setTitle("Erreur");
                    alertDialog.setMessage(errorResponse.optString("error"));
                    alertDialog.setNeutralButton("OK", (dialog, which) -> {
                    });
                    alertDialog.show();
                } else {
                    Log.e("CAREER", "Echec de l'enregistrement d'un parcours", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
