package fr.info.pl2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.LoginController;
import fr.info.pl2020.controller.SemestersListController;

public class LoginActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    public void login(View view) {
        startActivity(new Intent(LoginActivity.this, SemestersListActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText emailInput = findViewById(R.id.emailInput);
        String email = emailInput.getText().toString();

        EditText passwordInput = findViewById(R.id.passwordInput);
        String password = emailInput.getText().toString();

        new LoginController().authenticate(this, email, password);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Cliquez une deuxième fois pour quitter l'application", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }
}
