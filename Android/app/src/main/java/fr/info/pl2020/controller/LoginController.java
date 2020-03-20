package fr.info.pl2020.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.activity.SemestersListActivity;
import fr.info.pl2020.manager.AuthenticationManager;
import fr.info.pl2020.service.LoginService;

public class LoginController {
    public void authenticate(Context context, String email, String password, boolean startNextActivity) {
        new LoginService().authenticate(email, password, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == HttpStatus.SC_OK) {
                    try {
                        String token = response.getString("token");
                        AuthenticationManager.setToken(token);
                        if (startNextActivity) {
                            context.startActivity(new Intent(context, SemestersListActivity.class));
                        }
                        ((Activity) context).finish();
                    } catch (JSONException e) {
                        Log.e("LOGIN", "Echec de la récupération du token", e);
                    }
                } else {
                    Log.e("LOGIN", "Statut HTTP de la réponse inattendu (Code: " + statusCode + ")");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    TextView loginErrorTextView = ((Activity) context).findViewById(R.id.loginErrorTextView);
                    loginErrorTextView.setText(R.string.bad_credentials);
                    loginErrorTextView.setVisibility(View.VISIBLE);
                } else {
                    Log.e("LOGIN", "Echec de l'authentification (Code: " + statusCode + ")", throwable);
                    TextView loginErrorTextView = ((Activity) context).findViewById(R.id.loginErrorTextView);
                    loginErrorTextView.setText(R.string.server_connection_error);
                    loginErrorTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
