package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ListView;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.CareerController;

public class CareerListActivity extends ToolbarIntegratedActivity {
    private ListView careerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_list_activity);
        this.careerList=findViewById(R.id.CareerListView);

        // Toolbar
        new ToolbarConfig()
                .build();
    }
    @Override
    protected void onResume() {
        super.onResume();
        new CareerController().getAllCareers (this, this.careerList);
    }
}
