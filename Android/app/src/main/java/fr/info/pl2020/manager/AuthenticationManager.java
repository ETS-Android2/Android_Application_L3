package fr.info.pl2020.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import fr.info.pl2020.activity.LoginActivity;

public class AuthenticationManager {
    public String getToken() {
        return AuthenticationManagerStorage.getToken();
    }

    public void setToken(String token) {
        AuthenticationManagerStorage.setToken(token);
    }

    public void callLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("hasAParent", true);
        intent.putExtras(b);
        context.startActivity(intent);
    }
}

/**
 * Cette classe existe seulement parce qu'on ne peut pas mock de classe static avec PowerMock dans des tests Android
 * On pourra potentiellement la remplacer par le local storage du téléphone...
 */
class AuthenticationManagerStorage {
    private static String token = "";
    private final static String HEADER_PREFIX = "Bearer ";

    static String getToken() {
        return HEADER_PREFIX.concat(token);
    }

    static void setToken(String token) {
        AuthenticationManagerStorage.token = token;
    }
}
