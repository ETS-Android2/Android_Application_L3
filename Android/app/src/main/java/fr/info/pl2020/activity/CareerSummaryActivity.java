package fr.info.pl2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.controller.TeachingUnitController;

public class CareerSummaryActivity extends AppCompatActivity {
    private ListView listTU ;
    private ListView semesters ;
    private CareerController careerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_summary);
        this.semesters  = findViewById(R.id.Semester_career_summary);

        // Récupération de la liste des UE
        this.careerController = new CareerController();
        this.careerController.getCareer(this ,() -> {});















    }







}
