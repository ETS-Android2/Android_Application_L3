package fr.info.pl2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SemestersListController;
import fr.info.pl2020.manager.AuthenticationManager;

public class SemestersListActivity extends ToolbarIntegratedActivity {

    private ListView semesterList;
    private boolean doubleBackToExitPressedOnce = false;

    //for drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    //hamburger
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters_list);
        super.init(0, true, true);
        this.semesterList = findViewById(R.id.semesterListView);

        //for hamburger
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // for drawer
        mDrawerList = findViewById(R.id.navList);
        addDrawerItems();
        onItemClick();
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
        String[] parcoursNavArray = {"Afficher le parcours", "Editer le parcours", "Se déconnecter"};
        mAdapter = new ArrayAdapter<String>(SemestersListActivity.this, android.R.layout.simple_list_item_1, parcoursNavArray);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    private void onItemClick() {
        this.mDrawerList.setOnItemClickListener((parent, view, position, id) -> {
            switch ((int) id) {
                case 0:
                    Intent careerSummary = new Intent(SemestersListActivity.this, CareerSummaryActivity.class);
                    startActivity(careerSummary);
                    Toast.makeText(SemestersListActivity.this, "Vous avez cliqué sur Afficher le parcours", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(SemestersListActivity.this, "Vous avez cliqué sur Editer le parcours", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    logout();
                    break;
                default:
                    break;
            }
        });
    }

    public void logout() {
        new AuthenticationManager().logout(SemestersListActivity.this, new Intent(this, SemestersListActivity.class));
    }
}
