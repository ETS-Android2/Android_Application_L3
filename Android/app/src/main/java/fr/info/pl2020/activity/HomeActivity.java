package fr.info.pl2020.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import fr.info.pl2020.R;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.controller.HomeController;

import static fr.info.pl2020.controller.HomeController.StartActivity.CAREER_LIST;
import static fr.info.pl2020.controller.HomeController.StartActivity.CAREER_SUMMARY;
import static fr.info.pl2020.controller.HomeController.StartActivity.SEMESTER_LIST;

public class HomeActivity extends AppCompatActivity {

    private HomeController homeController;
    private boolean doubleBackToExitPressedOnce = false;
    private CareerController careerController = new CareerController();



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
        AlertDialog.Builder builderPopUp = new AlertDialog.Builder(this);

        final EditText namePopUp =new EditText(this);
        namePopUp.setText("Nom du parcours");
        namePopUp.setInputType(InputType.TYPE_CLASS_TEXT);
        final String[] items = {"Parcours Principal", "Partager le parcours"};
        final ArrayList itemsSelected = new ArrayList();



        builderPopUp.setTitle("Enregistrez un nouveau Parcours");
        builderPopUp.setView(namePopUp);

        builderPopUp.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            itemsSelected.add(which);
                        }
                        else if (itemsSelected.contains(which)){
                            itemsSelected.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = namePopUp.getText().toString();
                        careerController.createCareer(HomeActivity.this, name, itemsSelected.contains(0), itemsSelected.contains(1) ) ;

                        homeController.changeActivity(HomeActivity.this, SEMESTER_LIST );

                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog popUp= builderPopUp.create();
        popUp.show();
    }
    public void goEdit(View view){


        this.homeController.changeActivity(this, SEMESTER_LIST);
    }

    public void goMainCareer(View view) {
        this.homeController.changeActivity(this, CAREER_SUMMARY);
    }

    public void goMyCareersList(View view) {
        this.homeController.changeActivity(this, CAREER_LIST);
    }

}
