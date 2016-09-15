package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import org.weeklyhour.MainActivity.R;

/**
 * Created by admin on 2016-09-14.
 */
public class parentViewHolder extends ParentViewHolder {
    public RoundCornerProgressBar progressbar;
    public TextView taskName;
    public TextView day;
    public ImageView toggleArrow;

    public parentViewHolder(final View itemView) {
        super(itemView);

        taskName = (TextView) itemView.findViewById(R.id.taskName);
        progressbar = (RoundCornerProgressBar) itemView.findViewById(R.id.RoundCornerProgressBar);
        day = (TextView) itemView.findViewById(R.id.day);
        toggleArrow = (ImageView) itemView.findViewById(R.id.toggleArrowImage);

        //onBind때 해야하지만 collapseView, expandView가 protected라서 Adapter에서 접근불가
        toggleArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExpanded()){
                    collapseView();
                    toggleArrow.setImageResource(R.drawable.arrow_down);
                }
                else{
                    expandView();
                    toggleArrow.setImageResource(R.drawable.arrow_up);
                }
            }
        });
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }
}
