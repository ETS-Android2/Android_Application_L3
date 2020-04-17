package fr.info.pl2020.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.LoginController;
import fr.info.pl2020.util.FunctionsUtils;

public class LoginActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private boolean hasParent = false;
    private LoginController loginController;
    private TextView errorTextView;
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        changeBackground();
    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
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
        } else if (!FunctionsUtils.isEmail(email)) {
            this.displayErrorMessage(R.string.login_error_bad_email);
            this.passwordInput.requestFocus();
            return;
        } else if (!FunctionsUtils.isGoodPassword(password)) {
            this.displayErrorMessage(R.string.login_error_bad_password);
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

    private void changeBackground() {
        View view = findViewById(R.id.login_page);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource(R.drawable.background_landscape);
        } else {
            view.setBackgroundResource(R.drawable.login);
        }
    }

    public void goRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
