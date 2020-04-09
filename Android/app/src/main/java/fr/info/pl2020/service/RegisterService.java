package fr.info.pl2020.service;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;
import fr.info.pl2020.manager.HttpClientManager;

public class RegisterService {
    private final String urn ="/register";
    public void verify(String name, String lastname, String email, String password,  AsyncHttpResponseHandler responseHandler) {
        JSONObject message = new JSONObject();
        try {
            message.put("firstname",name);
            message.put("lastname",lastname);
            message.put("email", email);
            message.put("password", password);
            StringEntity entity = new StringEntity(message.toString());
            HttpClientManager.post(urn, entity, false, responseHandler);
        } catch (Exception e){
            Log.e("REGISTER_SERVICE", "Echec de la construction du message", e);
        }
    }
}
