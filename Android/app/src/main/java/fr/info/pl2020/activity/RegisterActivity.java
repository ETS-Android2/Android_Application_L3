package fr.info.pl2020.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.RegisterController;
import fr.info.pl2020.util.FunctionsUtils;

public class RegisterActivity extends AppCompatActivity {
    private TextView loginView;
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

        //go to Login page
        this.loginView = findViewById(R.id.register);
        loginView.setOnClickListener(v -> this.finish());
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
        View view = (View) findViewById(R.id.register_page);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource(R.drawable.background_landscape);
        } else {
            view.setBackgroundResource(R.drawable.login);
        }
    }

    public void register(View view) {
        hideErrorMessage();
        String name = this.nameRegister.getText().toString();
        String lastname = this.lastNameRegister.getText().toString();
        String email = this.emailRegister.getText().toString();
        String password = this.passwordRegister.getText().toString();
        String confirmpassword = this.confirmPasswordRegister.getText().toString();

        //error on data
        if(error(name, lastname, email, password, confirmpassword)){return;}
        this.registerController.authenticate(this, name, lastname, email, password, confirmpassword);
    }

    private void displayErrorMessage(int resId) {
        this.errorTextView.setText(resId);
        this.errorTextView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage() {
        TextView registerErrorTextView = findViewById(R.id.registerErrorTextView);
        registerErrorTextView.setText("");
        registerErrorTextView.setVisibility(View.GONE);
    }

    private boolean error(String name, String lastname, String email, String password, String confirmpassword){
        if (lastNameError(lastname)){return true;}
        else if (nameError(name)){return true;}
        else if (emailError(email)){return true;}
        else if (passwordError(password)){return true;}
        else if (confirmPasswordError(confirmpassword)){return true;}
        else return notMatchingPassword(password, confirmpassword);
    }
    private boolean lastNameError(String lastname){
        if (lastname.trim().isEmpty()) {
            this.displayErrorMessage(R.string.register_lastname_missing_error);
            this.lastNameRegister.requestFocus();
            return true;
        }
        else if(lastname.trim().length() < 2 ||  lastname.trim().length() > 49){
            this.displayErrorMessage(R.string.register_lastname_length_error);
            this.lastNameRegister.requestFocus();
            return true;
        }
        return false;
    }
    private boolean nameError(String name){
        if (name.trim().isEmpty()) {
            this.displayErrorMessage(R.string.register_name_missing_error);
            this.nameRegister.requestFocus();
            return true;
        }
        else if(name.trim().length() < 2 ||  name.trim().length() > 49){
            this.displayErrorMessage(R.string.register_name_length_error);
            this.nameRegister.requestFocus();
            return true;
        }
        return false;
    }
    private boolean emailError(String email){
        if (email.trim().isEmpty()) {
            this.displayErrorMessage(R.string.register_email_missing_error);
            this.emailRegister.requestFocus();
            return true;
        }
        else if(email.trim().length() > 49){
            this.displayErrorMessage(R.string.register_email_length_error);
            this.emailRegister.requestFocus();
            return true;
        }
        else if(!FunctionsUtils.isEmail(email.trim())){
            this.displayErrorMessage(R.string.register_bad_email_error);
            this.emailRegister.requestFocus();
            return true;
        }
        return false;
    }
    private boolean passwordError(String password){
        if (password.trim().isEmpty()) {
            this.displayErrorMessage(R.string.register_password_missing_error);
            this.passwordRegister.requestFocus();
            return true;
        }
        else if(password.trim().length() < 5 ||  password.trim().length() > 49){
            this.displayErrorMessage(R.string.register_password_length_error);
            this.passwordRegister.requestFocus();
            return true;
        }
        return false;
    }
    private boolean confirmPasswordError(String confrimPassword) {
        if (confrimPassword.trim().isEmpty()) {
            this.displayErrorMessage(R.string.register_confirmpassword_missing_error);
            this.confirmPasswordRegister.requestFocus();
            return true;
        }
        else if(confrimPassword.trim().length() < 5 ||  confrimPassword.trim().length() > 49){
            this.displayErrorMessage(R.string.register_confirmpassword_length_error);
            this.passwordRegister.requestFocus();
            return true;
        }
        return false;
    }
    private boolean notMatchingPassword(String password, String confirmPassword){
        if (!password.equals(confirmPassword)){
            this.displayErrorMessage(R.string.register_matchingpassword_missing_error);
            this.passwordRegister.requestFocus();
            this.confirmPasswordRegister.requestFocus();
            return true;
        }
        return false;
    }
}
