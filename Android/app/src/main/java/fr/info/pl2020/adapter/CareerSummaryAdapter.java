package fr.info.pl2020.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.info.pl2020.R;
import fr.info.pl2020.model.TeachingUnit;

public class CareerSummaryAdapter extends BaseAdapter {
    private Context context;
    private List<Object> teachingUnits;

    public CareerSummaryAdapter(Context context, List<Object> teachingUnits) {
        this.context = context;
        this.teachingUnits = teachingUnits;
    }

    @Override
    public int getCount() {
        return teachingUnits.size();
    }

    @Override
    public Object getItem(int position) {
        return this.teachingUnits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Object o = getItem(position);
        if (o instanceof TeachingUnit) {
            convertView = layoutInflater.inflate(R.layout.career_summary_list_item, null);

            TextView teachingUnitTextView = convertView.findViewById(R.id.career);
            teachingUnitTextView.setText(((TeachingUnit) o).getName());
        } else if (o instanceof String) {
            convertView = layoutInflater.inflate(R.layout.career_summary_list_header, null);

            TextView semesterName = convertView.findViewById(R.id.semesterName);
            semesterName.setText((String) o);
        }
        return convertView;
    }
}
