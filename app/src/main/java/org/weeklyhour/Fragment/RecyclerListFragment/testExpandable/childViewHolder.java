package org.weeklyhour.Fragment.RecyclerListFragment.testExpandable;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import org.weeklyhour.Activity.R;

/**
 * Created by admin on 2016-09-14.
 */
public class childViewHolder extends ChildViewHolder {

    public TextView mCrimeDateText;

    public childViewHolder(View itemView) {
        super(itemView);

        mCrimeDateText = (TextView) itemView.findViewById(R.id.child_list_item_crime_date_text_view);
    }
}
