package fr.info.pl2020.service;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import fr.info.pl2020.manager.HttpClientManager;

public class LoginService {
    private final String urn = "/login";

    public void authenticate(String email, String password, AsyncHttpResponseHandler responseHandler) {
        JSONObject message = new JSONObject();
        try {
            message.put("email", email);
            message.put("password", password);
            StringEntity entity = new StringEntity(message.toString());
            HttpClientManager.post(urn, entity, null, responseHandler);

        } catch (Exception e) {
            Log.e("LOGIN_SERVICE", "Echec de la construction du message", e);
        }
    }
}
