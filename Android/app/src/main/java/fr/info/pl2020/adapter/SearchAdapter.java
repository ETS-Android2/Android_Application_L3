package fr.info.pl2020.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import fr.info.pl2020.R;
import fr.info.pl2020.activity.TeachingUnitListActivity;
import fr.info.pl2020.model.TeachingUnitListContent.TeachingUnit;

public class SearchAdapter extends BaseAdapter {

    private final static String SEMESTER_TEMPLATE = "Semestre %d : ";

    private Context context;
    private List<TeachingUnit> teachingUnitList;
    private boolean displaySemester;

    public SearchAdapter(Context context, List<TeachingUnit> teachingUnitList, boolean displaySemester) {
        this.context = context;
        this.teachingUnitList = teachingUnitList;
        this.displaySemester = displaySemester;
    }


    @Override
    public int getCount() {
        return teachingUnitList.size();
    }

    @Override
    public Object getItem(int position) {
        return teachingUnitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchTeachingUnitViewHolder holder;

        if (convertView == null) {
            holder = new SearchTeachingUnitViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.search_teachingunit_item, null);

            holder.semesterName = convertView.findViewById(R.id.searchResultSemesterName);
            holder.name = convertView.findViewById(R.id.searchResultName);

            convertView.setTag(holder);
        } else {
            holder = (SearchTeachingUnitViewHolder) convertView.getTag();
        }

        TeachingUnit tu = teachingUnitList.get(position);

        if (displaySemester) {
            String semesterName = String.format(Locale.FRENCH, SEMESTER_TEMPLATE, tu.getSemester());
            holder.semesterName.setText(semesterName);
        } else {
            holder.semesterName.setVisibility(View.GONE);
        }

        holder.name.setText(tu.getName());
        convertView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, TeachingUnitListActivity.class);
            intent.putExtra(TeachingUnitListActivity.ARG_SEMESTER_ID, tu.getSemester());
            intent.putExtra(TeachingUnitListActivity.ARG_FOCUS_TU_ID, tu.getId());
            context.startActivity(intent);
        });

        return convertView;
    }

    private static class SearchTeachingUnitViewHolder {
        private TextView semesterName;
        private TextView name;
    }
}
