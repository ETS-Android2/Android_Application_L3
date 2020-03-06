package fr.info.pl2020.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.TeachingUnitController;

public class TeachingUnitListActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
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
}
