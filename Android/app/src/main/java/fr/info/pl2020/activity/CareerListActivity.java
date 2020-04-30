package fr.info.pl2020.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerListAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.store.CareerListStore;

public class CareerListActivity extends ToolbarIntegratedActivity {

    public static final String ARG_MODE = "mode";

    public enum CareerListMode {
        STUDENT,
        PUBLIC
    }

    private CareerListMode mode = CareerListMode.STUDENT;
    private ListView careerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_list);

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey(ARG_MODE)) {
            this.mode = (CareerListMode) b.getSerializable(ARG_MODE);
        }

        // Toolbar
        new ToolbarConfig().enableDrawer(findViewById(R.id.careerListLayout)).build();

        this.careerList = findViewById(R.id.CareerListView);

        CareerController careerController = new CareerController();
        if (mode.equals(CareerListMode.STUDENT)) {
            careerController.getAllCareers(this, this::refreshList);
        } else if (mode.equals(CareerListMode.PUBLIC)) {
            careerController.getPublicCareers(this, this::refreshList);
        } else {
            finish();
        }
    }

    private void refreshList() {
        CareerListAdapter careerAdapter = new CareerListAdapter(CareerListActivity.this, new ArrayList<>(CareerListStore.CAREERS.values()), mode);
        careerList.setAdapter(careerAdapter);
    }

    @Override
    protected void onDestroy() {
        CareerListStore.clear();
        super.onDestroy();
    }
}
