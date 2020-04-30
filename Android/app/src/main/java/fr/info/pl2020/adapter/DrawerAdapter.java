package fr.info.pl2020.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.info.pl2020.R;
import fr.info.pl2020.activity.CareerListActivity;
import fr.info.pl2020.activity.CareerSummaryActivity;
import fr.info.pl2020.activity.HomeActivity;
import fr.info.pl2020.activity.LoginActivity;
import fr.info.pl2020.activity.SemestersListActivity;
import fr.info.pl2020.manager.AuthenticationManager;
import fr.info.pl2020.store.CareerStore;

import static fr.info.pl2020.activity.CareerListActivity.ARG_MODE;

public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private DrawerLayout drawerLayout;
    private List<String> items = new ArrayList<>();
    private HashMap<String, View.OnClickListener> eventByPositionMap = new HashMap<>();

    public DrawerAdapter(Context context, DrawerLayout drawerLayout) {
        this.context = context;
        this.drawerLayout = drawerLayout;
        init();
    }

    private void init() {
        items.add("Accueil");
        eventByPositionMap.put("Accueil", v -> {
            if (!(context instanceof HomeActivity)) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            } else {
                drawerLayout.closeDrawers();
            }
        });
        items.add("Parcours en cours");
        eventByPositionMap.put("Parcours en cours", v -> {
            if (!(context instanceof CareerSummaryActivity)) {
                Intent intent = new Intent(context, CareerSummaryActivity.class);
                if (CareerStore.getCurrentCareer() != null) {
                    intent.putExtra(CareerSummaryActivity.ARG_CAREER_ID, CareerStore.getCurrentCareer().getId());
                }
                ((Activity) context).finish();
                context.startActivity(intent);
            } else {
                drawerLayout.closeDrawers();
            }
        });
        items.add("Editer le parcours en cours");
        eventByPositionMap.put("Editer le parcours en cours", v -> {
            if (!(context instanceof SemestersListActivity)) {
                Intent intent = new Intent(context, SemestersListActivity.class);
                if (CareerStore.getCurrentCareer() != null) {
                    intent.putExtra(CareerSummaryActivity.ARG_CAREER_ID, CareerStore.getCurrentCareer().getId());
                }
                ((Activity) context).finish();
                context.startActivity(intent);
            } else {
                drawerLayout.closeDrawers();
            }
        });
        items.add("Mes parcours");
        eventByPositionMap.put("Mes parcours", v -> {
            Intent intent = new Intent(context, CareerListActivity.class);
            intent.putExtra(ARG_MODE, CareerListActivity.CareerListMode.STUDENT);
            context.startActivity(intent);
            ((Activity) context).finish();
        });
        items.add("Parcours publics");
        eventByPositionMap.put("Parcours publics", v -> {
            Intent intent = new Intent(context, CareerListActivity.class);
            intent.putExtra(ARG_MODE, CareerListActivity.CareerListMode.PUBLIC);
            context.startActivity(intent);
            ((Activity) context).finish();
        });


        items.add("Se déconnecter");
        eventByPositionMap.put("Se déconnecter", v -> new AuthenticationManager().logout(((Activity) context), new Intent(context, LoginActivity.class)));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_item, null);
        }

        String item = (String) getItem(position);
        TextView semesterTextView = convertView.findViewById(R.id.drawerItem);
        semesterTextView.setText(item);
        if (item == "Se déconnecter") {
            semesterTextView.setTextColor(Color.RED);
        }
        semesterTextView.setOnClickListener(eventByPositionMap.get(item));

        return convertView;
    }
}
