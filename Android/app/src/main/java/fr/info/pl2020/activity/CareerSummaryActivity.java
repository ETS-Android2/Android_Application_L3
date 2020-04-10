package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerSummaryAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.TeachingUnitListContent;

public class CareerSummaryActivity extends ToolbarIntegratedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_summary);

        DrawerLayout drawerLayout = findViewById(R.id.career_summary_layout);
        new ToolbarConfig().enableDrawer(drawerLayout).build();

        // Récupération de la liste des UE
        new CareerController().getCareer(this, () -> {
            CareerSummaryAdapter adapter = new CareerSummaryAdapter(CareerSummaryActivity.this, new ArrayList<>(TeachingUnitListContent.TEACHING_UNITS.values()));
            ListView listTU = findViewById(R.id.teBySemester);
            listTU.setAdapter(adapter);
        });
    }

}
