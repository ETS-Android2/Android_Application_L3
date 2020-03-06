package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SemestersListController;

public class SemestersListActivity extends AppCompatActivity {

    private ListView semesterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters_list);
        this.semesterList = findViewById(R.id.ListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SemestersListController().displaySemesterList(this, this.semesterList);
    }
}
