package fr.info.pl2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.info.pl2020.R;
import fr.info.pl2020.store.TeachingUnitListStore;

/**
 * An activity representing a single TeachingUnit detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TeachingUnitListActivity}.
 */
public class TeachingUnitDetailActivity extends AppCompatActivity {

    private int teachingUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachingunit_detail);

        this.teachingUnitId = getIntent().getIntExtra(TeachingUnitDetailFragment.ARG_ITEM_ID, 0);
        if (this.teachingUnitId == 0) {
            finish();
        }

        TeachingUnitListStore.setLastOpenedTeachingUnit(this.teachingUnitId);

        // Le bouton ajouter/retirer du parcours
        FloatingActionButton fab = findViewById(R.id.fab);
        boolean isSelected = TeachingUnitListStore.TEACHING_UNITS.get(this.teachingUnitId).isSelected();
        if (isSelected) {
            fab.setImageResource(R.drawable.ic_clear_white_24dp);
            fab.setOnClickListener(getClearListener());
        } else {
            fab.setImageResource(R.drawable.ic_check_white_24dp);
            fab.setOnClickListener(getAddListener());
        }

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added to its container so we don't need to manually add it.
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(TeachingUnitDetailFragment.ARG_ITEM_ID, this.teachingUnitId);
            TeachingUnitDetailFragment fragment = new TeachingUnitDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.teachingunit_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, TeachingUnitListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener getAddListener() {
        return view -> {
            ((FloatingActionButton) view).setImageResource(R.drawable.ic_clear_white_24dp);
            TeachingUnitListStore.TEACHING_UNITS.get(this.teachingUnitId).setSelected(true);
            Toast.makeText(this, "UE ajouté au parcours", Toast.LENGTH_SHORT).show();
            view.setOnClickListener(getClearListener());
        };
    }

    private View.OnClickListener getClearListener() {
        return view -> {
            ((FloatingActionButton) view).setImageResource(R.drawable.ic_check_white_24dp);
            TeachingUnitListStore.TEACHING_UNITS.get(this.teachingUnitId).setSelected(false);
            Toast.makeText(this, "UE retiré du parcours", Toast.LENGTH_SHORT).show();
            view.setOnClickListener(getAddListener());
        };
    }
}
