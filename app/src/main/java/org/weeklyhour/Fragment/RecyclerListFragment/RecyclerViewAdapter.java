package org.weeklyhour.Fragment.RecyclerListFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.weeklyhour.Activity.R;

import java.util.ArrayList;

/**
 * 어댑터는 RecyclerView에게 데이터를 전달해주는 역할을 한다.
 * 최초 선언시 선언된 부분의 Context와 전달할 데이터를 가진다
 * Created by admin on 2016-09-07.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Item> mItems;

    public RecyclerViewAdapter(ArrayList<Item> DataSet){
        mItems = DataSet;
    }

    /*
    * 별동작 안함 */
    public void setItems(ArrayList<Item> arrItems) {
        mItems = arrItems;
    }


    /*
    * 뷰홀더는 한번 선언된 데이터를 계속 가지고 새로 불러올 필요 없이 저장하는 역할을 한다.
    * 생성자의 인자인 View itemView에서 itemLayout에 선언된 TextView 객체들을 가지게된다.
    *
    * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.idNumber);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    /*
    * ViewHolder가 생성되었을 경우 동작한다.
    * 먼저 아이템의 레이아웃을 객체화한 뒤 뷰홀더와 연결한다.
    * 리턴값은 onBindViewHolder에서 받는다 */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_itemlayout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /*
    * holder는 방금 생성된 뷰홀더를 말하고, 포지션은 그 순서이다.
    * 순서에 맞춰서 mItems에서 실제 데이터를 뽑아서 홀더에 연결한다.
    * */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(mItems.get(position).idNumber);
        holder.content.setText(mItems.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
