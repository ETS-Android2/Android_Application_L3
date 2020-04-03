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
import fr.info.pl2020.activity.TeachingUnitDetailActivity;
import fr.info.pl2020.activity.TeachingUnitDetailFragment;
import fr.info.pl2020.activity.TeachingUnitListActivity;
import fr.info.pl2020.model.TeachingUnitListContent;


public class TeachingUnitAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private List<String> categoryList;
    private Map<String, List<TeachingUnitListContent.TeachingUnit>> teachingUnitMap;
    private final boolean isTwoPane;

    private final View.OnClickListener defaultOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int teachingUnitId = (Integer) view.getTag();
            if (isTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putInt(TeachingUnitDetailFragment.ARG_ITEM_ID, teachingUnitId);
                TeachingUnitDetailFragment fragment = new TeachingUnitDetailFragment();
                fragment.setArguments(arguments);
                ((TeachingUnitListActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.teachingunit_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, TeachingUnitDetailActivity.class);
                intent.putExtra(TeachingUnitDetailFragment.ARG_ITEM_ID, teachingUnitId);

                context.startActivity(intent);
            }
        }
    };

    public TeachingUnitAdapter(Context context, Map<String, List<TeachingUnitListContent.TeachingUnit>> items, boolean twoPane) {
        this.context = context;
        this.isTwoPane = twoPane;
        this.categoryList = new ArrayList<>(items.keySet());
        this.teachingUnitMap = items;
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

        CategoryViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.teachingunit_list_category_content, null);

            holder = new CategoryViewHolder();
            holder.name = convertView.findViewById(R.id.categoryName);
            convertView.setTag(holder);
        } else {
            holder = (CategoryViewHolder) convertView.getTag();
        }

        String category = (String) getGroup(groupPosition);
        holder.name.setTypeface(null, Typeface.BOLD);
        holder.name.setText(category);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TeachingUnitViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.teachingunit_list_content, null);

            holder = new TeachingUnitViewHolder();
            holder.name = convertView.findViewById(R.id.content);
            holder.checkBox = convertView.findViewById(R.id.teachingunit_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (TeachingUnitViewHolder) convertView.getTag();
        }

        TeachingUnitListContent.TeachingUnit tu = (TeachingUnitListContent.TeachingUnit) getChild(groupPosition, childPosition);
        holder.name.setText(tu.getName());
        holder.name.setTag(tu.getId());
        holder.name.setOnClickListener(defaultOnClickListener);

        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);

        holder.checkBox.setChecked(tu.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> tu.setSelected(isChecked));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class CategoryViewHolder {
        private TextView name;
    }

    private static class TeachingUnitViewHolder {
        private TextView name;
        private CheckBox checkBox;
    }
}
