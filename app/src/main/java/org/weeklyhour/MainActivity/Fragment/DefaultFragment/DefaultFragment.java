package org.weeklyhour.MainActivity.Fragment.DefaultFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.weeklyhour.MainActivity.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DefaultFragment extends Fragment {
    /**
        * The fragment argument representing the section number for this
                * fragment.
                */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public DefaultFragment() {
        }

        /**
         * 자신을 객체로 만든 뒤 내놓는다
         */
    public static DefaultFragment newInstance(int sectionNumber) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_default, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}
