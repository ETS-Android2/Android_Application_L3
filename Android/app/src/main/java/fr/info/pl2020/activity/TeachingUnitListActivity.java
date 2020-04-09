package fr.info.pl2020.activity;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.TeachingUnitAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.controller.TeachingUnitController;
import fr.info.pl2020.model.Semester;
import fr.info.pl2020.model.TeachingUnitListContent;

import static java.util.stream.Collectors.toList;

/**
 * An activity representing a list of TeachingUnits. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TeachingUnitDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TeachingUnitListActivity extends ToolbarIntegratedActivity {

    public static final String ARG_SEMESTER_ID = "semester_id";
    public static final String ARG_FOCUS_TU_ID = "teaching_unit_id";

    public static boolean isTwoPane;
    private Semester currentSemester;

    private TeachingUnitController teachingUnitController = new TeachingUnitController();
    private CareerController careerController = new CareerController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachingunit_list);
        int focusedTeachingUnitId = 0;

        // Récupération des attributs du semestre
        Bundle b = getIntent().getExtras();
        if (b != null && b.getInt(ARG_SEMESTER_ID) != 0) {
            this.currentSemester = new Semester(b.getInt(ARG_SEMESTER_ID));
            focusedTeachingUnitId = b.getInt(ARG_FOCUS_TU_ID);
        } else {
            finish();
        }

        // Initialisation de la toolbar
        super.init(this.currentSemester.getId(), false, true);
        super.setTitle(this.currentSemester.getName());

        // Le bouton enregistrer
        FloatingActionButton fab = findViewById(R.id.fab_save_career);
        fab.setOnClickListener(view -> careerController.saveCareer(TeachingUnitListActivity.this));

        if (findViewById(R.id.teachingunit_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            isTwoPane = true;
        }

        // Récupération de la liste des UE
        this.teachingUnitController = new TeachingUnitController();
        this.teachingUnitController.updateTeachingUnits(this, currentSemester.getId());

        // Si c'est la recherche qui a créé cette page, on redirige vers l'UE demandé
        // Très bancale comme solution, mais à défaut d'en avoir une autre on va faire comme ça.
        if (focusedTeachingUnitId != 0) {
            ExpandableListView expandableListView = findViewById(R.id.teachingunit_list);
            final int finalFocusedTeachingUnitId = focusedTeachingUnitId;
            expandableListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (expandableListView.getAdapter() != null) {
                        TeachingUnitListContent.TeachingUnit teachingUnit = TeachingUnitListContent.TEACHING_UNITS.get(finalFocusedTeachingUnitId);
                        int groupId = new ArrayList<>(TeachingUnitListContent.getTeachingUnitByCategory().keySet()).indexOf(teachingUnit.getCategory());
                        int childId = TeachingUnitListContent.getTeachingUnitByCategory().get(teachingUnit.getCategory()).stream().map(TeachingUnitListContent.TeachingUnit::getId).collect(toList()).indexOf(teachingUnit.getId());

                        expandableListView.expandGroup(groupId, false);
                        expandableListView.getExpandableListAdapter().getChildView(groupId, childId, true, null, null).findViewById(R.id.content).performClick();
                        expandableListView.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }

                    return false;
                }
            });
        }
    }

    @Override
    public void onResume() {
        ExpandableListView expandableListView = findViewById(R.id.teachingunit_list);
        TeachingUnitAdapter adapter = (TeachingUnitAdapter) expandableListView.getExpandableListAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        TeachingUnitListContent.clear();
        TeachingUnitListContent.setLastOpenedTeachingUnit(0);
        super.onDestroy();
    }
}
