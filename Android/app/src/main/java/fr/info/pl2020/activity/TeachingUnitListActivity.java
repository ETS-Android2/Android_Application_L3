package fr.info.pl2020.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.TeachingUnitController;
import fr.info.pl2020.model.Semester;
import fr.info.pl2020.model.TeachingUnitListContent;

/**
 * An activity representing a list of TeachingUnits. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TeachingUnitDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TeachingUnitListActivity extends AppCompatActivity {

    public static final String ARG_SEMESTER_ID = "semester_id";

    private boolean isTwoPane;
    private boolean doubleBackToExitPressedOnce = false;
    private Semester currentSemester;

    private TeachingUnitController teachingUnitController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachingunit_list);

        // Récupération des attributs du semestre
        Bundle b = getIntent().getExtras();
        if (b != null && b.getInt(ARG_SEMESTER_ID) != 0) {
            this.currentSemester = new Semester(b.getInt(ARG_SEMESTER_ID));
        } else {
            finish();
        }

        // TOOLBAR
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(this.currentSemester.getName());
        }

        // Le bouton enregistrer
        FloatingActionButton fab = findViewById(R.id.fab_save_career);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Parcours enregistré", Snackbar.LENGTH_LONG) //TODO
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.teachingunit_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            isTwoPane = true;
        }
        Log.d("TEST", "CURRENT SEMESTER : " + currentSemester.getId());
        // Récupération de la liste des UE
        this.teachingUnitController = new TeachingUnitController();
        this.teachingUnitController.updateTeachingUnits(this, currentSemester.getId(), isTwoPane);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.teachingUnitController.setupExpandableListView(this, isTwoPane);
/*
        ExpandableListView expandableListView = findViewById(R.id.teachingunit_list);
        setupExpandableListView(expandableListView);
*/
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

    @Override
    protected void onDestroy() {
        TeachingUnitListContent.clear();
        TeachingUnitListContent.setLastOpenedTeachingUnit(0);
        super.onDestroy();
    }
}
