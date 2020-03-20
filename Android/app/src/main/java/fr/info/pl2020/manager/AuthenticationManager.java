package fr.info.pl2020.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import fr.info.pl2020.activity.LoginActivity;

public class AuthenticationManager {

    private static String token = "";
    private final static String HEADER_PREFIX = "Bearer ";

    public static String getToken() {
        return HEADER_PREFIX.concat(token);
    }

    public static void setToken(String token) {
        AuthenticationManager.token = token;
    }

    public static void callLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("hasAParent", true);
        intent.putExtras(b);
        context.startActivity(intent);
    }
}
