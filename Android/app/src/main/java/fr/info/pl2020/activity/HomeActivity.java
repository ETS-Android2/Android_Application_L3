package fr.info.pl2020.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.HomeController;
import fr.info.pl2020.store.CareerStore;

import static fr.info.pl2020.controller.HomeController.StartActivity.CAREER_LIST;
import static fr.info.pl2020.controller.HomeController.StartActivity.CAREER_LIST_PUBLIC;
import static fr.info.pl2020.controller.HomeController.StartActivity.CAREER_SUMMARY;
import static fr.info.pl2020.controller.HomeController.StartActivity.CREATE_CAREER;
import static fr.info.pl2020.controller.HomeController.StartActivity.SEMESTER_LIST;

public class HomeActivity extends AppCompatActivity {

    private HomeController homeController;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.homeController = new HomeController();
        changeBackground();
    }

    @Override
    protected void onResume() {
        CareerStore.clear();
        super.onResume();
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

    public void goCreate(View view) {
        new HomeController().changeActivity(HomeActivity.this, CREATE_CAREER);
    }

    public void goEdit(View view) {
        this.homeController.changeActivity(this, SEMESTER_LIST);
    }

    public void goMainCareer(View view) {
        this.homeController.changeActivity(this, CAREER_SUMMARY);
    }

    public void goMyCareersList(View view) {
        this.homeController.changeActivity(this, CAREER_LIST);
    }

    public void goPublicCareersList(View view) {
        this.homeController.changeActivity(this, CAREER_LIST_PUBLIC);
    }
}
