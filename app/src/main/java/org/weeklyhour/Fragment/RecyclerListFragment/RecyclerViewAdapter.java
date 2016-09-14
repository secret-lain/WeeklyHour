package org.weeklyhour.Fragment.RecyclerListFragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import org.weeklyhour.Activity.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * 어댑터는 RecyclerView에게 데이터를 전달해주는 역할을 한다.
 * 최초 선언시 RecyclerView에서 사용될 ArrayList<parentitem> 을 갖는다.
 * Created by admin on 2016-09-07.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<parentitem> mParentitems;
    private static final Random rnd = new Random();

    public RecyclerViewAdapter(ArrayList<parentitem> DataSet){
        mParentitems = DataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RoundCornerProgressBar progressbar;
        public TextView taskName;
        public TextView day;

        public ViewHolder(View itemView) {
            super(itemView);

            taskName = (TextView) itemView.findViewById(R.id.taskName);
            progressbar = (RoundCornerProgressBar) itemView.findViewById(R.id.RoundCornerProgressBar);
            day = (TextView) itemView.findViewById(R.id.day);
        }
    }

    /*
    * ViewHolder가 생성되었을 경우 동작한다.
    * 먼저 아이템의 레이아웃을 객체화한 뒤 뷰홀더와 연결한다.
    * 리턴값은 onBindViewHolder에서 받는다 */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_parent_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /*
    * holder는 방금 생성된 뷰홀더를 말하고, 포지션은 그 순서이다.
    * 순서에 맞춰서 mItems에서 실제 데이터를 뽑아서 홀더에 연결한다.
    * */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //배경은 흰색으로 고정
        holder.progressbar.setProgressBackgroundColor(Color.parseColor("#FFFFFF"));

        //Adapter에 적용된 ArrayList에서 순차적으로 꺼내와서 실제 객체에 적용
        holder.progressbar.setProgressColor(mParentitems.get(position).progressBarColor);
        holder.progressbar.setMax(mParentitems.get(position).maxDay);
        holder.progressbar.setProgress(rnd.nextInt(mParentitems.get(position).maxDay));

        holder.taskName.setText(mParentitems.get(position).taskName);
        holder.day.setText((int)holder.progressbar.getProgress() + " / " + mParentitems.get(position).maxDay);
    }

    @Override
    public int getItemCount() {
        return mParentitems.size();
    }
}
