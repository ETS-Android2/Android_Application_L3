package fr.info.pl2020.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.info.pl2020.R;
import fr.info.pl2020.activity.TeachingUnitDetailsActivity;
import fr.info.pl2020.model.TeachingUnit;

public class TeachingUnitAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> categoryList;
    private Map<String, List<TeachingUnit>> teachingUnitMap;

    public TeachingUnitAdapter(Context context, Map<String, List<TeachingUnit>> teachingUnitMap) {
        this.context = context;
        this.categoryList = new ArrayList<>(teachingUnitMap.keySet());
        this.teachingUnitMap = teachingUnitMap;
    }

    @Override
    public int getGroupCount() {
        return this.categoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Objects.requireNonNull(this.teachingUnitMap.get(this.categoryList.get(groupPosition))).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(this.teachingUnitMap.get(this.categoryList.get(groupPosition))).get(childPosition);
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
            convertView = layoutInflater.inflate(R.layout.list_category, null);
        }

        String categoryNb = (String) getGroup(groupPosition);
        TextView categoryTextView = convertView.findViewById(R.id.listTitle);
        categoryTextView.setTypeface(null, Typeface.BOLD);
        categoryTextView.setText(categoryNb);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        boolean firstTime = convertView == null;
        if (firstTime) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_teaching_unit, null);
        }

        TeachingUnit teachingUnit = (TeachingUnit) getChild(groupPosition, childPosition);
        TextView teachingUnitTextView = convertView.findViewById(R.id.expandedListItem);
        teachingUnitTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeachingUnitDetailsActivity.class);
            Bundle b = new Bundle();
            b.putInt("teachingUnitId", teachingUnit.getId());
            intent.putExtras(b);
            context.startActivity(intent);
        });

        CheckBox teachingUnitCheckbox = convertView.findViewById(R.id.teachinUnitCheckbox);
        teachingUnitTextView.setText(teachingUnit.getName());
        teachingUnitCheckbox.setTag(teachingUnit);
        if (teachingUnit.isSelectedByStudent()) {/*
            if (firstTime) {
                Log.i("TEST", "click : " + teachingUnit.getName());
                teachingUnitCheckbox.performClick();
            }*/

        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
