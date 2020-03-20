package fr.info.pl2020.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.LoginController;

public class LoginActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private boolean hasParent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            hasParent = b.getBoolean("hasAParent");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideErrorMessage();
    }

    public void login(View view) {
        hideErrorMessage();
        EditText emailInput = findViewById(R.id.emailInput);
        String email = emailInput.getText().toString();

        EditText passwordInput = findViewById(R.id.passwordInput);
        String password = passwordInput.getText().toString();
        new LoginController().authenticate(this, email, password, !hasParent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_click_for_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    private void hideErrorMessage() {
        TextView loginErrorTextView = findViewById(R.id.loginErrorTextView);
        loginErrorTextView.setText("");
        loginErrorTextView.setVisibility(View.GONE);
    }
}
