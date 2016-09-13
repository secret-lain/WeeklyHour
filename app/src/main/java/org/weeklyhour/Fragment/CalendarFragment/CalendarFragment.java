package org.weeklyhour.Fragment.CalendarFragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import org.weeklyhour.Activity.R;


/**
 */
public class CalendarFragment extends Fragment {

    public CalendarFragment() {
        // Required empty public constructor
    }


    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendar = (CalendarView) rootView.findViewById(R.id.calendarView);
        return rootView;
    }

    public interface OnFragmentInteractionListner{
        public void OnFragmentInteration(Uri Uri);
    }
}
