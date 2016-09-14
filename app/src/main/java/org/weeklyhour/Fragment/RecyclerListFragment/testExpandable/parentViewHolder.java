package org.weeklyhour.Fragment.RecyclerListFragment.testExpandable;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import org.weeklyhour.Activity.R;

/**
 * Created by admin on 2016-09-14.
 */
public class parentViewHolder extends ParentViewHolder{
        public TextView mCrimeTitleTextView;
        public ImageButton mParentDropDownArrow;

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }

    public parentViewHolder(View itemView) {
            super(itemView);

            mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.parent_list_item_crime_title_text_view);
            mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);

            mParentDropDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isExpanded()){
                        collapseView();
                    } else{
                        expandView();
                    }
                }
            });
        }
}