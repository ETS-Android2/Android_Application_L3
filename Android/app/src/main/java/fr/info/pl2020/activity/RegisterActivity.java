package fr.info.pl2020.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.RegisterController;

import static fr.info.pl2020.util.FunctionsUtils.isEmail;
import static fr.info.pl2020.util.FunctionsUtils.readTextView;

public class RegisterActivity extends AppCompatActivity {
    private TextView errorTextView;
    private RegisterController registerController;
    private EditText nameRegister;
    private EditText lastNameRegister;
    private EditText emailRegister;
    private EditText passwordRegister;
    private EditText confirmPasswordRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        overridePendingTransition(0, 0);
        changeBackground();
        //register
        errorTextView = findViewById(R.id.registerErrorTextView);
        nameRegister = findViewById(R.id.nameRegister);
        lastNameRegister = findViewById(R.id.lastNameRegister);
        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        confirmPasswordRegister = findViewById(R.id.confirmPasswordRegister);

        registerController = new RegisterController();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideErrorMessage();
    }

    private void changeBackground() {
        View view = findViewById(R.id.register_page);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource(R.drawable.login_landscape);
        } else {
            view.setBackgroundResource(R.drawable.login_portrait);
        }
    }

    public void register(View view) {
        hideErrorMessage();
        String name = readTextView(this.nameRegister);
        String lastname = readTextView(this.lastNameRegister);
        String email = readTextView(this.emailRegister);
        String password = readTextView(this.passwordRegister);
        String confirmPassword = readTextView(this.confirmPasswordRegister);

        try {
            validate(name, 2, 49, R.string.register_name_missing_error, R.string.register_name_length_error, this.nameRegister);
            validate(lastname, 2, 49, R.string.register_lastname_missing_error, R.string.register_lastname_length_error, this.lastNameRegister);
            validate(email, 1, 49, R.string.register_email_missing_error, R.string.register_email_length_error, this.emailRegister);
            validate(password, 5, 49, R.string.register_password_missing_error, R.string.register_password_length_error, this.passwordRegister);
            validate(confirmPassword, 5, 49, R.string.register_confirm_password_missing_error, R.string.register_confirm_password_length_error, this.confirmPasswordRegister);

            if (!isEmail(email)) {
                this.displayErrorMessage(R.string.register_bad_email_error, this.emailRegister);
                throw new IllegalArgumentException();
            } else if (!password.equals(confirmPassword)) {
                this.displayErrorMessage(R.string.register_not_matching_passwords_error, this.passwordRegister);
                throw new IllegalArgumentException();
            }

            this.registerController.authenticate(this, name, lastname, email, password);
        } catch (RuntimeException ignored) {
        }
    }

    private void displayErrorMessage(int resId, View toFocus) {
        this.errorTextView.setText(resId);
        this.errorTextView.setVisibility(View.VISIBLE);

        if (toFocus != null) {
            toFocus.requestFocus();
        }
    }

    private void hideErrorMessage() {
        TextView registerErrorTextView = findViewById(R.id.registerErrorTextView);
        registerErrorTextView.setText("");
        registerErrorTextView.setVisibility(View.GONE);
    }

    private void validate(String value, int minSize, int maxSize, int missingErrorMessage, int lengthErrorMessage, TextView view) {
        if (value.length() >= minSize && value.length() <= maxSize) {
            return;
        }

        displayErrorMessage(value.isEmpty() ? missingErrorMessage : lengthErrorMessage, view);
        throw new IllegalArgumentException();
    }

    public void goLogin(View view) {
        this.finish();
    }
}
