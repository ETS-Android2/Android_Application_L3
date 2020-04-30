package fr.info.pl2020.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.info.pl2020.R;
import fr.info.pl2020.activity.CareerListActivity;
import fr.info.pl2020.activity.CareerSummaryActivity;
import fr.info.pl2020.activity.HomeActivity;
import fr.info.pl2020.activity.SemestersListActivity;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.Career;

import static fr.info.pl2020.activity.CareerSummaryActivity.ARG_CAREER_ID;

public class CareerListAdapter extends BaseAdapter {
    private Context context;
    private List<Career> careerList;

    public CareerListAdapter(Context context, List<Career> careers) {
        this.context = context;
        this.careerList = careers;
    }

    @Override
    public int getCount() {
        return careerList.size();
    }

    @Override
    public Object getItem(int position) {
        return careerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.career_list_item, null);
        }

        Career career = (Career) getItem(position);
        TextView careerTextView = convertView.findViewById(R.id.careers);
        ImageView editSelectedCareer = convertView.findViewById(R.id.edit_button);
        ImageView deleteSelectedCareer = convertView.findViewById(R.id.delete_button);

        careerTextView.setText(career.getName());


        editSelectedCareer.setOnClickListener(v -> {
            Intent intent = new Intent(context, SemestersListActivity.class);
            context.startActivity(intent);
        });

        deleteSelectedCareer.setOnClickListener(v -> {
            CareerController careerController = new CareerController();
            careerController.deleteCareer(context, career);
        });

        careerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CareerSummaryActivity.class);
            intent.putExtra(ARG_CAREER_ID, career.getId());
            context.startActivity(intent);
        });


        return convertView;
    }
}
