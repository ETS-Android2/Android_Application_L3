package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.TeachingUnitController;

public class TeachingUnitActivity extends AppCompatActivity {

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        this.expandableListView = findViewById(R.id.expandableListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new TeachingUnitController().displayTeachingUnits(this, this.expandableListView);
    }
}


