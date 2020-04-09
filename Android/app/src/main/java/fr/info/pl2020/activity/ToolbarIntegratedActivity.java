package fr.info.pl2020.activity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SearchController;

public abstract class ToolbarIntegratedActivity extends AppCompatActivity {

    private static final int MIN_CHAR_FOR_SEARCH = 3 ;
    private ActionBar actionBar;
    private int semesterId;
    private boolean isDrawerEnabled;
    private boolean isSearchEnabled;

    protected void init(int semesterId, boolean isDrawerEnabled, boolean isSearchEnabled) {
        this.actionBar = getSupportActionBar();
        if (this.actionBar == null) {
            throw new IllegalStateException("L'activité ne peut pas hérité de " + this.getLocalClassName() + ", elle ne comporte aucune Toolbar.");
        }

        this.semesterId = semesterId;
        this.isDrawerEnabled = isDrawerEnabled;
        this.isSearchEnabled = isSearchEnabled;
    }

    protected void setTitle(String title) {
        actionBar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        if (isSearchEnabled) {
            initSearch(menu);
        }

        return true;
    }

    private void initSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        View searchBackground = findViewById(R.id.search_background);
        if (searchBackground == null) {
            Log.e("TOOLBAR", "SearchListView is null, is it properly included in the activity layout?");
            return;
        }

        searchItem.setVisible(true);
        searchBackground.setOnClickListener(v -> {
            searchView.setQuery("", false);
            searchView.setIconified(true);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchController searchController = new SearchController();
                searchController.searchTeachingUnit(ToolbarIntegratedActivity.this, query, semesterId);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TextView searchErrorTextView = findViewById(R.id.searchErrorTextView);
                searchErrorTextView.setVisibility(View.GONE);

                if (newText.isEmpty()) {
                    searchBackground.setVisibility(View.GONE);
                    return false;
                } else if (newText.length() < MIN_CHAR_FOR_SEARCH) {
                    searchBackground.setVisibility(View.VISIBLE);
                    return false;
                } else {
                    searchBackground.setVisibility(View.VISIBLE);
                    SearchController searchController = new SearchController();
                    searchController.searchTeachingUnit(ToolbarIntegratedActivity.this, newText, semesterId);
                    return true;
                }
            }
        });
    }
}
