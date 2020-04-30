package fr.info.pl2020.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.info.pl2020.R;
import fr.info.pl2020.activity.CareerListActivity;
import fr.info.pl2020.activity.CareerSummaryActivity;
import fr.info.pl2020.component.EditCareerPopup;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.Career;

import static fr.info.pl2020.activity.CareerSummaryActivity.ARG_CAREER_ID;

public class CareerListAdapter extends BaseAdapter {
    private Context context;
    private List<Career> careerList;
    private CareerListActivity.CareerListMode mode;

    public CareerListAdapter(Context context, List<Career> careers, CareerListActivity.CareerListMode mode) {
        this.context = context;
        this.careerList = careers;
        this.mode = mode;
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

        Career career = (Career) getItem(position);

        if (mode.equals(CareerListActivity.CareerListMode.STUDENT)) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.career_list_item, null);

            ImageView editSelectedCareer = convertView.findViewById(R.id.edit_button);
            ImageView deleteSelectedCareer = convertView.findViewById(R.id.delete_button);

            editSelectedCareer.setOnClickListener(v -> {
                new EditCareerPopup(context, career, false);
            });

            deleteSelectedCareer.setOnClickListener(v -> {
                CareerController careerController = new CareerController();
                careerController.deleteCareer(context, career);

                this.careerList.removeIf(c -> c.getId() == career.getId());
                notifyDataSetChanged();
            });
        } else if (mode.equals(CareerListActivity.CareerListMode.PUBLIC)) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.public_career_list_item, null);
        } else {
            throw new IllegalArgumentException("Illegal mode");
        }

        TextView careerTextView = convertView.findViewById(R.id.careerName);

        careerTextView.setText(career.getName());

        careerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CareerSummaryActivity.class);
            intent.putExtra(ARG_CAREER_ID, career.getId());
            context.startActivity(intent);
        });

        return convertView;
    }
}
