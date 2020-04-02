package fr.info.pl2020.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SemestersListController;

public class SemestersListActivity extends AppCompatActivity {

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
        this.semesterList = findViewById(R.id.semesterListView);

        //for hamburger
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_open, R.string.drawer_close);
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
        String[] parcoursNavArray = { "Afficher le parcours", "Editer le parcours", "Se déconnecter" };
        mAdapter = new ArrayAdapter<String>(SemestersListActivity.this, android.R.layout.simple_list_item_1, parcoursNavArray);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    private void onItemClick() {
        this.mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) id) {
                    case 0:
                        Toast.makeText(SemestersListActivity.this, "WIP not implemented yet", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(SemestersListActivity.this, "WIP not implemented yet", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(SemestersListActivity.this, "Se déconnecter", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
