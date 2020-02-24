package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SemesterController;

public class SemestersActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    //List<String> expandableListTitle;
    //HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        this.expandableListView = findViewById(R.id.expandableListView);
        new SemesterController().displaySemester(this, this.expandableListView);
    }
}
