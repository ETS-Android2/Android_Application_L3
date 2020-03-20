package fr.info.pl2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.TeachingUnitController;

public class TeachingUnitDetailsActivity extends AppCompatActivity {

    private int teachingUnitId;
    private TextView nameTextView;
    private TextView codeTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_unit_details);
        Bundle b = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("UE");
            actionBar.show();
        }

        if (b != null) {
            this.teachingUnitId = b.getInt("teachingUnitId");
        } else {
            finish();
        }

        this.nameTextView = findViewById(R.id.teaching_unit_title);
        this.codeTextView = findViewById(R.id.teaching_unit_code);
        this.descriptionTextView = findViewById(R.id.teaching_unit_description);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(TeachingUnitDetailsActivity.this, ProfilActivity.class);
            // TODO ajouter l'identifiant du prof dans l'Intent
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new TeachingUnitController().displayTeachingUnitDetails(this, this.nameTextView, this.codeTextView, this.descriptionTextView, this.teachingUnitId);
    }
}
