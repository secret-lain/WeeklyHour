package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import org.weeklyhour.MainActivity.R;

/**
 * Created by admin on 2016-09-14.
 */
public class childViewHolder extends ChildViewHolder {
    public EditText memo;
    public Button editButton;
    public Button startButton;

    public childViewHolder(View itemView) {
        super(itemView);

        memo = (EditText) itemView.findViewById(R.id.memoEditText);
        editButton = (Button) itemView.findViewById(R.id.taskEditButton);
        startButton = (Button) itemView.findViewById(R.id.taskStartButton);
    }
}
