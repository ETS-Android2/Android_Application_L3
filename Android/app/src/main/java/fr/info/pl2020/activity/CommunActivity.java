package fr.info.pl2020.activity;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.SearchController;

public class CommunActivity extends AppCompatActivity {

    private static final int MIN_CHAR_FOR_SEARCH = 3 ;


    public boolean onCreateOptionsMenu(Menu menu, Activity activity) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        View searchBackground = findViewById(R.id.search_background);
        searchBackground.setOnClickListener(v -> {
            searchView.setQuery("", false);
            searchView.setIconified(true);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchController searchController = new SearchController();
                searchController.searchTeachingUnit(activity, query, 0);
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
                    searchController.searchTeachingUnit(activity, newText, 0);
                    return true;
                }
            }
        });

        return true;
    }
}
