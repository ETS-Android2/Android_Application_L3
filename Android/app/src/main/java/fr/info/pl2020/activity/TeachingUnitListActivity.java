package fr.info.pl2020.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fr.info.pl2020.R;
import fr.info.pl2020.adapter.TeachingUnitAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.controller.SearchController;
import fr.info.pl2020.controller.TeachingUnitController;
import fr.info.pl2020.model.Semester;
import fr.info.pl2020.model.TeachingUnit;
import fr.info.pl2020.store.TeachingUnitListStore;

import static java.util.stream.Collectors.toList;

/**
 * An activity representing a list of TeachingUnits. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TeachingUnitDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TeachingUnitListActivity extends ToolbarIntegratedActivity implements SearchView.OnQueryTextListener {

    public static final String ARG_SEMESTER_ID = "semester_id";
    public static final String ARG_FOCUS_TU_ID = "teaching_unit_id";

    public static boolean isTwoPane;
    private Semester currentSemester;

    private CareerController careerController = new CareerController();
    private SearchController searchController = new SearchController();

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
            if (focusedTeachingUnitId != 0) {
                overridePendingTransition(0, 0);
            }
        } else {
            finish();
        }

        // Initialisation de la toolbar
        new ToolbarConfig()
                .setTitle(this.currentSemester.getName())
                .enableSearch(this)
                .build();

        // Le bouton enregistrer
        FloatingActionButton fab = findViewById(R.id.fab_save_career);
        fab.setOnClickListener(view -> careerController.saveCareer(TeachingUnitListActivity.this, currentSemester.getId()));

        if (findViewById(R.id.teachingunit_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            isTwoPane = true;
        }

        // Récupération de la liste des UE
        new TeachingUnitController().updateTeachingUnits(this, currentSemester.getId());

        // Si c'est la recherche qui a créé cette page, on redirige vers l'UE demandé
        // Très bancale comme solution, mais à défaut d'en avoir une autre on va faire comme ça.
        if (focusedTeachingUnitId != 0) {
            ExpandableListView expandableListView = findViewById(R.id.teachingunit_list);
            final int finalFocusedTeachingUnitId = focusedTeachingUnitId;
            expandableListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (expandableListView.getAdapter() != null) {
                        TeachingUnit teachingUnit = TeachingUnitListStore.TEACHING_UNITS.get(finalFocusedTeachingUnitId);
                        int groupId = new ArrayList<>(TeachingUnitListStore.getTeachingUnitByCategory().keySet()).indexOf(teachingUnit.getCategory());
                        int childId = TeachingUnitListStore.getTeachingUnitByCategory().get(teachingUnit.getCategory()).stream().map(TeachingUnit::getId).collect(toList()).indexOf(teachingUnit.getId());

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
        TeachingUnitListStore.clear();
        TeachingUnitListStore.setLastOpenedTeachingUnit(0);
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.searchController.searchTeachingUnit(this, query, this.currentSemester.getId());
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        TextView searchErrorTextView = findViewById(R.id.searchErrorTextView);
        View searchBackground = findViewById(R.id.search_background);
        searchErrorTextView.setVisibility(View.GONE);

        if (newText.isEmpty()) {
            searchBackground.setVisibility(View.GONE);
            return false;
        } else if (newText.length() < MIN_CHAR_FOR_SEARCH) {
            searchBackground.setVisibility(View.VISIBLE);
            return false;
        } else {
            searchBackground.setVisibility(View.VISIBLE);
            this.searchController.searchTeachingUnit(this, newText, this.currentSemester.getId());
            return true;
        }
    }
}
