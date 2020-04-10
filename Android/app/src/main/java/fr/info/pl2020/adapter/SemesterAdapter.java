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
import fr.info.pl2020.activity.TeachingUnitListActivity;
import fr.info.pl2020.model.Semester;

public class SemesterAdapter extends BaseAdapter {

    private Context context;
    private List<Semester> semesters;

    public SemesterAdapter(Context context, List<Semester> semesters) {
        this.context = context;
        this.semesters = semesters;
    }


    @Override
    public int getCount() {
        return semesters.size();
    }

    @Override
    public Object getItem(int position) {
        return this.semesters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.semester_list_item, null);
        }

        Semester semester = (Semester) getItem(position);
        TextView semesterTextView = convertView.findViewById(R.id.semester);
        semesterTextView.setText(semester.getName());
        semesterTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeachingUnitListActivity.class);
            intent.putExtra(TeachingUnitListActivity.ARG_SEMESTER_ID, semester.getId());
            context.startActivity(intent);
        });

        return convertView;
    }
}
