package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.drawerlayout.widget.DrawerLayout;
import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerSummaryAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.TeachingUnitListContent;

public class  CareerSummaryActivity extends ToolbarIntegratedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_summary);

        DrawerLayout drawerLayout = findViewById(R.id.career_summary_layout);
        new ToolbarConfig().enableDrawer(drawerLayout).build();

        // Récupération de la liste des UE
        new CareerController().getCareer(this, () -> {
            Map<Integer, List<TeachingUnitListContent.TeachingUnit>> teachingUnitBySemester = TeachingUnitListContent.getTeachingUnitBySemester();
            List<Object> listItem = new ArrayList<>();
            teachingUnitBySemester.forEach((semesterId, teachingUnits) -> {
                listItem.add("Semestre " + semesterId);
                listItem.addAll(teachingUnits);
            });
            ListView summaryCareerList = findViewById(R.id.summaryCareerList);
            CareerSummaryAdapter adapter = new CareerSummaryAdapter(this, listItem);
            summaryCareerList.setAdapter(adapter);
        });
    }
    @Override
    protected void onDestroy() {
        TeachingUnitListContent.clear();
        TeachingUnitListContent.setLastOpenedTeachingUnit(0);
        super.onDestroy();
    }

}
