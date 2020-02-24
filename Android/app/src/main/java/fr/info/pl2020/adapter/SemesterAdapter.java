package fr.info.pl2020.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.info.pl2020.R;

public class SemesterAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> semesterList;
    private Map<String, List<String>> categoryMap;

    public SemesterAdapter(Context context, Map<String, List<String>> categoryMap) {
        this.context = context;
        this.semesterList = new ArrayList<>(categoryMap.keySet());
        this.categoryMap = categoryMap;
    }


    @Override
    public int getGroupCount() {
        return this.semesterList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Objects.requireNonNull(this.categoryMap.get(this.semesterList.get(groupPosition))).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.semesterList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(this.categoryMap.get(this.semesterList.get(groupPosition))).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_semester, null);
        }

        String semesterNb = (String) getGroup(groupPosition);
        TextView semesterTextView = convertView.findViewById(R.id.listTitle);
        semesterTextView.setTypeface(null, Typeface.BOLD);
        semesterTextView.setText(semesterNb);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_category, null);
        }

        String categoryName = (String) getChild(groupPosition, childPosition);
        TextView categoryTextView = convertView.findViewById(R.id.expandedListItem);
        categoryTextView.setText(categoryName);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
