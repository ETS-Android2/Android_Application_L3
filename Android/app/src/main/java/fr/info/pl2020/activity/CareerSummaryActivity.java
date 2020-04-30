package fr.info.pl2020.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerSummaryAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.TeachingUnit;
import fr.info.pl2020.store.CareerStore;
import fr.info.pl2020.store.TeachingUnitListStore;

public class CareerSummaryActivity extends ToolbarIntegratedActivity {

    public static final String ARG_CAREER_ID = "career_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_summary);


        // Récupération des attributs de la carrière
        int careerId = 0;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            careerId = b.getInt(ARG_CAREER_ID);
        }

        DrawerLayout drawerLayout = findViewById(R.id.career_summary_layout);
        new ToolbarConfig().enableDrawer(drawerLayout).enableExport(careerId).build();

        // Récupération de la liste des UE
        new CareerController().getCareer(this, careerId, () -> { //TODO exporter dans une méthode display
            Map<Integer, List<TeachingUnit>> teachingUnitBySemester = CareerStore.getTeachingUnitBySemester();
            if (teachingUnitBySemester.size() != 0) {
                // Si le parcours n'est pas vide
                List<Object> listItem = new ArrayList<>();
                teachingUnitBySemester.forEach((semesterId, teachingUnits) -> {
                    listItem.add("Semestre " + semesterId);
                    listItem.addAll(teachingUnits);
                });
                ListView summaryCareerList = CareerSummaryActivity.this.findViewById(R.id.summaryCareerList);
                CareerSummaryAdapter adapter = new CareerSummaryAdapter(CareerSummaryActivity.this, listItem);
                summaryCareerList.setAdapter(adapter);
            } else {
                // Si le parcours est vide
                LayoutInflater inflater = (LayoutInflater) CareerSummaryActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.empty_career_summary_list, null);

                LinearLayout linearLayout = CareerSummaryActivity.this.findViewById(R.id.summaryCareer);
                linearLayout.addView(v);
            }
        });
    }

    @Override
    protected void onDestroy() {
        TeachingUnitListStore.clear();
        TeachingUnitListStore.setLastOpenedTeachingUnit(0);
        super.onDestroy();
    }
}
