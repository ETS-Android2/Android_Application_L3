package fr.info.pl2020.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.info.pl2020.activity.CareerListActivity;
import fr.info.pl2020.activity.CareerSummaryActivity;
import fr.info.pl2020.component.EditCareerPopup;

import static fr.info.pl2020.activity.CareerListActivity.ARG_MODE;

public class HomeController {

    public enum StartActivity {
        SEMESTER_LIST,
        CAREER_SUMMARY,
        CAREER_LIST,
        CAREER_LIST_PUBLIC
    }

    public void changeActivity(Context context, StartActivity activityName) {
        switch (activityName) {
            case SEMESTER_LIST:
                new EditCareerPopup(context, null, true);
                break;

            case CAREER_SUMMARY:
                context.startActivity(new Intent(context, CareerSummaryActivity.class));
                break;

            case CAREER_LIST:
                Intent careerIntent = new Intent(context, CareerListActivity.class);
                careerIntent.putExtra(ARG_MODE, CareerListActivity.CareerListMode.STUDENT);
                context.startActivity(careerIntent);
                break;

            case CAREER_LIST_PUBLIC:
                Intent careerPublicIntent = new Intent(context, CareerListActivity.class);
                careerPublicIntent.putExtra(ARG_MODE, CareerListActivity.CareerListMode.PUBLIC);
                context.startActivity(careerPublicIntent);
                break;

            default:
                Log.e("HOME", "changeActivity() - Activité non reconnu");
        }
    }
}
