package fr.info.pl2020.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import fr.info.pl2020.R;
import fr.info.pl2020.model.TeachingUnitListContent;

/**
 * A fragment representing a single TeachingUnit detail screen.
 * This fragment is either contained in a {@link TeachingUnitListActivity}
 * in two-pane mode (on tablets) or a {@link TeachingUnitDetailActivity}
 * on handsets.
 */
public class TeachingUnitDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private TeachingUnitListContent.TeachingUnit currentTeachingUnit;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeachingUnitDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            currentTeachingUnit = TeachingUnitListContent.TEACHING_UNITS.get(getArguments().getInt(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(currentTeachingUnit.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.teachingunit_detail, container, false);

        if (currentTeachingUnit != null) {

            ((TextView) rootView.findViewById(R.id.teaching_unit_title)).setText(currentTeachingUnit.getName());
            ((TextView) rootView.findViewById(R.id.teaching_unit_code)).setText(currentTeachingUnit.getCode());
            ((TextView) rootView.findViewById(R.id.teaching_unit_description)).setText(currentTeachingUnit.getDescription());
        }

        return rootView;
    }
}
