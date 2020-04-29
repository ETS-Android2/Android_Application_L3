package fr.info.pl2020.activity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import fr.info.pl2020.R;
import fr.info.pl2020.adapter.DrawerAdapter;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.Career;

public abstract class ToolbarIntegratedActivity extends AppCompatActivity {

    public static final int MIN_CHAR_FOR_SEARCH = 3;
    private ActionBar actionBar;
    private ToolbarConfig config;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private void init(ToolbarConfig config) {
        this.actionBar = getSupportActionBar();
        if (this.actionBar == null) {
            throw new IllegalStateException("L'activité ne peut pas hérité de " + this.getLocalClassName() + ", elle ne comporte aucune Toolbar.");
        }

        this.config = config;
        if (config.title != null) {
            actionBar.setTitle(config.title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        if (config.isSearchEnabled) {
            initSearch(menu);
        }

        if (config.isDrawerEnabled) {
            initDrawer();
        }

        if (config.isExportEnabled) {
            initExport(menu);
        }

        return true;
    }

    private void initDrawer() {
        try {
            // Hamburger
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, config.drawerLayout, R.string.drawer_open, R.string.drawer_close);
            actionBarDrawerToggle.syncState();
            this.actionBar.setDisplayHomeAsUpEnabled(true);

            // Drawer
            ListView navList = findViewById(R.id.drawerNavList);
            if (navList == null) {
                Log.e("TOOLBAR", "ListView 'drawerNavList' n'a pas été trouvé dans la RootView, est-il correctement inclue dans le layout de l'activité ?");
                return;
            }

            DrawerAdapter adapter = new DrawerAdapter(this, config.drawerLayout);
            navList.setAdapter(adapter);
        } catch (ClassCastException e) {
            Log.e("TOOLBAR", "RootView n'est pas un DrawerLayout.");
        } catch (Exception e) {
            Log.e("TOOLBAR", "Echec de l'intialisation du Drawer", e);
        }
    }

    private void initSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        View searchBackground = findViewById(R.id.search_background);
        if (searchBackground == null) {
            Log.e("TOOLBAR", "SearchListView est null, est-elle correctement incluse dans le layout de l'activité ?");
            return;
        }

        searchItem.setVisible(true);
        searchBackground.setOnClickListener(v -> {
            searchView.setQuery("", false);
            searchView.setIconified(true);
        });

        searchView.setOnQueryTextListener(config.onQueryTextListener);
    }

    private void initExport(Menu menu) {
        MenuItem exportItem = menu.findItem(R.id.action_export);
        exportItem.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle != null && actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_export_txt:
                new CareerController().downloadCareer(this, 1, Career.ExportFormat.TXT);
                return true;

            case R.id.action_export_pdf:
                new CareerController().downloadCareer(this, 1, Career.ExportFormat.PDF);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class ToolbarConfig {
        private String title;
        private boolean isDrawerEnabled;
        private boolean isSearchEnabled;
        private boolean isExportEnabled;
        private SearchView.OnQueryTextListener onQueryTextListener;
        private DrawerLayout drawerLayout;
        private int currentCareerId;

        ToolbarConfig setTitle(String title) {
            this.title = title;
            return this;
        }

        ToolbarConfig enableSearch(SearchView.OnQueryTextListener onQueryTextListener) {
            this.onQueryTextListener = onQueryTextListener;
            this.isSearchEnabled = true;
            return this;
        }

        ToolbarConfig enableDrawer(DrawerLayout drawerLayout) {
            this.drawerLayout = drawerLayout;
            this.isDrawerEnabled = true;
            return this;
        }

        ToolbarConfig enableExport(int careerId) {
            this.currentCareerId = careerId;
            this.isExportEnabled = true;
            return this;
        }

        void build() {
            ToolbarIntegratedActivity.this.init(this);
        }
    }
}
