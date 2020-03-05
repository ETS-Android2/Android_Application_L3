package fr.info.pl2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SemestersListController;

public class SemestersListActivity extends AppCompatActivity {

    private View ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters_list);
        this.ListView = findViewById(R.id.ListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SemestersListController().displaySemester(this, this.ListView);
    }
}
