package fr.info.pl2020.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerSummaryAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.TeachingUnitListContent;

public class CareerSummaryActivity extends ToolbarIntegratedActivity {

    private boolean doubleBackToExitPressedOnce;

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

}
