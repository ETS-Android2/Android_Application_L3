package fr.info.pl2020.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.activity.LoginActivity;
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

                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
                else {
                    Log.e("SEMESTER", "Echec de la récupération de la liste des semestres (Code: " + statusCode + ")");
                    Toast.makeText(context, "La connexion avec le serveur a échoué", Toast.LENGTH_SHORT).show();
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
