package fr.info.pl2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerSummaryAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.controller.TeachingUnitController;
import fr.info.pl2020.model.TeachingUnitListContent;
import fr.info.pl2020.service.CareerService;

public class CareerSummaryActivity extends AppCompatActivity {
    private ListView listTU ;
    private CareerController careerController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_summary);
        listTU = findViewById(R.id.teBySemester);

        // Récupération de la liste des UE
        this.careerController = new CareerController();
        this.careerController.getCareer(this,() -> {
            CareerSummaryAdapter adapter = new CareerSummaryAdapter(CareerSummaryActivity.this, new ArrayList<>( TeachingUnitListContent.TEACHING_UNITS.values()));
            listTU.setAdapter(adapter);
        });


    }



}
