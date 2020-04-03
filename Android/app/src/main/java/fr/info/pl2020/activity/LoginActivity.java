package fr.info.pl2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
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
    private LoginController loginController;
    private TextView registerView;
    private TextView errorTextView;
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            hasParent = b.getBoolean("hasAParent");
        }
        this.loginController = new LoginController();
        this.errorTextView = findViewById(R.id.loginErrorTextView);
        this.emailInput = findViewById(R.id.emailInput);
        this.passwordInput = findViewById(R.id.passwordInput);

        //go to register page
        this.registerView = findViewById(R.id.register);
        registerView.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideErrorMessage();
    }

    public void login(View view) {
        hideErrorMessage();
        String email = this.emailInput.getText().toString();
        String password = this.passwordInput.getText().toString();

        if (email.trim().isEmpty()) {
            this.displayErrorMessage(R.string.login_error_missing_email);
            this.emailInput.requestFocus();
            return;
        } else if (password.trim().isEmpty()) {
            this.displayErrorMessage(R.string.login_error_missing_password);
            this.passwordInput.requestFocus();
            return;
        }
        this.loginController.authenticate(this, email, password, !hasParent);
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

    private void displayErrorMessage(int resId) {
        this.errorTextView.setText(resId);
        this.errorTextView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage() {
        TextView loginErrorTextView = findViewById(R.id.loginErrorTextView);
        loginErrorTextView.setText("");
        loginErrorTextView.setVisibility(View.GONE);
    }
}
