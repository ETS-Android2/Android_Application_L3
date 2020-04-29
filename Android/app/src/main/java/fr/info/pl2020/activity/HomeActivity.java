package fr.info.pl2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.HomeController;

public class HomeActivity extends AppCompatActivity {

    private HomeController homeController;
    private boolean hasParent = false;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.homeController = new HomeController();
        changeBackground();
    }

    private void changeBackground() {
        View view = findViewById(R.id.home_page);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource(R.drawable.home_landscape);
        } else {
            view.setBackgroundResource(R.drawable.home_portrait);
        }
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

    public void goCreate(View view){
        this.homeController.changeActivity(this, "SemesterListActivity" ,!hasParent);
    }
    public void goEdit(View view){
        this.homeController.changeActivity(this, "SemesterListActivity",!hasParent);
    }
    public void goMainCareer(View view){
        this.homeController.changeActivity(this, "CareerSummaryActivity",!hasParent);
    }
    public void goMyCareersList(View view){
        this.homeController.changeActivity(this, "CareerListActivity",!hasParent);
    }

}
