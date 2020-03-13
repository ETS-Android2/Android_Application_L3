package fr.info.pl2020.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.controller.TeachingUnitController;
import fr.info.pl2020.model.TeachingUnit;

public class TeachingUnitListActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private CareerController careerController;
    private Set<Integer> teachingUnitIds;
    private int semesterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_unit);
        Bundle b = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(b != null ? b.getString("semesterName") : "Semestre");
            actionBar.show();
        }
        this.expandableListView = findViewById(R.id.expandableListView);
        this.careerController = new CareerController();
        this.teachingUnitIds = new HashSet<>();
        if (b != null) {
            this.semesterId = b.getInt("semesterId");
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new TeachingUnitController().displayTeachingUnits(this, this.expandableListView, this.semesterId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void validateCareer(View view) {
        careerController.saveCareer(this, new ArrayList<>(this.teachingUnitIds));
    }

    public void toggleCheckBox(View view) {
        CheckBox cb = (CheckBox) view;
        TeachingUnit tu = (TeachingUnit) cb.getTag();
        if (cb.isChecked()) {
            this.teachingUnitIds.add(tu.getId());
        } else {
            this.teachingUnitIds.remove(tu.getId());
        }
    }
}
