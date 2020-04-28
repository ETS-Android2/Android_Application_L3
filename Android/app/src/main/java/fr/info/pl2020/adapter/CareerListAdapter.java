package fr.info.pl2020.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.info.pl2020.R;
import fr.info.pl2020.activity.CareerSummaryActivity;
import fr.info.pl2020.model.Career;

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

        Career career= (Career) getItem(position);
        TextView careerTextView = convertView.findViewById(R.id.careers);
        careerTextView.setText(career.getName());
        careerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CareerSummaryActivity.class);
            intent.putExtra("career_id", career.getId());
            context.startActivity(intent);
        });

        return convertView;
    }
}
