package fr.info.pl2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import fr.info.pl2020.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView loginView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(0, 0);
        //go to register page
        this.loginView = findViewById(R.id.register);
        loginView.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

    }
}
