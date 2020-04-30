package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerListAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.store.CareerListStore;

public class CareerListActivity extends ToolbarIntegratedActivity {

    private ListView careerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_list);
        this.careerList = findViewById(R.id.CareerListView);

        // Toolbar
        new ToolbarConfig().enableDrawer(findViewById(R.id.careerListLayout)).build();

        new CareerController().getAllCareers(this, () -> {
            CareerListAdapter careerAdapter = new CareerListAdapter(CareerListActivity.this, new ArrayList<>(CareerListStore.CAREERS.values()));
            careerList.setAdapter(careerAdapter);
        });
    }

    @Override
    protected void onDestroy() {
        CareerListStore.clear();
        super.onDestroy();
    }
}
