package fr.info.pl2020.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import fr.info.pl2020.activity.CareerListActivity;
import fr.info.pl2020.activity.CareerSummaryActivity;
import fr.info.pl2020.activity.SemestersListActivity;

public class HomeController {
    public void changeActivity(Context context, String activityName, boolean startNextActivity){
        if (startNextActivity) {
            if (activityName.equals("SemesterListActivity")){
                context.startActivity(new Intent(context, SemestersListActivity.class));
            }
            else if (activityName.equals("CareerSummaryActivity")){
                context.startActivity(new Intent(context, CareerSummaryActivity.class));
            }
            else if (activityName.equals("CareerListActivity")){
                context.startActivity(new Intent(context, CareerListActivity.class));
            }
        }
    }
}
