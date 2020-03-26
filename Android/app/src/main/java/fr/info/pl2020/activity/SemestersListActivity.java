package fr.info.pl2020.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SemestersListController;

public class SemestersListActivity extends AppCompatActivity {

    private ListView semesterList;
    private boolean doubleBackToExitPressedOnce = false;

    //for drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters_list);
        this.semesterList = findViewById(R.id.semesterListView);

        mDrawerList = findViewById(R.id.navList);
        addDrawerItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SemestersListController().displaySemesterList(this, this.semesterList);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_click_for_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
    private void addDrawerItems() {
        String[] parcoursNavArray = { "Afficher le parcours", "Editer le parcours", "Se déconnecter" };
        mAdapter = new ArrayAdapter<String>(SemestersListActivity.this, android.R.layout.simple_list_item_1, parcoursNavArray);
        mDrawerList.setAdapter(mAdapter);
    }

}
