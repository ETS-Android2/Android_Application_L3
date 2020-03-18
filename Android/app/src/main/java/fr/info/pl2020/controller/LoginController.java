package fr.info.pl2020.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.service.LoginService;

public class LoginController {
    public void authenticate(Context context, String email, String password) {
        new LoginService().authenticate(email, password, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == HttpStatus.SC_OK) {
                    try {
                        String token = response.getString("token");
                    } catch (JSONException e) {
                        Log.e("LOGIN", "Echec de la récupération du token", e);
                    }
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("LOGIN", "Echec de l'authentification (Code: " + statusCode + ")", throwable);
                Toast.makeText(context, "La connexion avec le serveur a échoué", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
