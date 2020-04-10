package fr.info.pl2020.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.info.pl2020.R;

import fr.info.pl2020.model.TeachingUnitListContent;

public class CareerSummaryAdapter extends BaseAdapter {
    private Context context;
    private List<TeachingUnitListContent.TeachingUnit>  teachingUnits ;

    public CareerSummaryAdapter(Context context, List<TeachingUnitListContent.TeachingUnit> teachingUnits){
        this.context=context;
        this.teachingUnits=teachingUnits;
    }

    @Override
    public int getCount() {
        return teachingUnits.size();
    }

    @Override
    public Object getItem(int position) {
        return this.teachingUnits.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.career_summary_list_item, null);
        }

        TeachingUnitListContent.TeachingUnit teachingUnit = (TeachingUnitListContent.TeachingUnit) getItem(position);
        TextView teachingUnitTextView = convertView.findViewById(R.id.career);
        teachingUnitTextView.setText(teachingUnit.getName());
        return convertView;
    }
}
