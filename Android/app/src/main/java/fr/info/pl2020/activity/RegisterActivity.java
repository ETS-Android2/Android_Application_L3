package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import fr.info.pl2020.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView loginView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(0, 0);
        Snackbar.make(findViewById(R.id.nameRegisterLayout), "Warning : Not implemented yet !", BaseTransientBottomBar.LENGTH_INDEFINITE).show();

        //go to Login page
        this.loginView = findViewById(R.id.register);
        loginView.setOnClickListener(v -> this.finish());
    }
}
