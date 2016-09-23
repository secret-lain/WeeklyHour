package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;

import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.item;
import org.weeklyhour.MainActivity.R;

import java.util.List;
import java.util.Random;

import io.realm.Realm;

/**
 * 어댑터는 RecyclerView에게 데이터를 전달해주는 역할을 한다.
 * 최초 선언시 RecyclerView에서 사용될 ArrayList<parentItem> 을 갖는다.
 * Created by admin on 2016-09-07.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.itemViewHolder> implements setRealmResultClearCallback{
    //Inner Class, ViewHolder
    public static class itemViewHolder extends RecyclerView.ViewHolder{
        public BlurLayout mBlurLayout;
        public RoundCornerProgressBar mProgressBar;
        public TextView mTextView;
        public View hoverLayer;

        public itemViewHolder(View v, View hover) {
            super(v);

            mBlurLayout = (BlurLayout) v.findViewById(R.id.itemBlurLayout);
            mTextView = (TextView) v.findViewById(R.id.itemTextView);
            mProgressBar = (RoundCornerProgressBar) v.findViewById(R.id.itemRoundCornerProgressBar);
            hoverLayer = hover;
        }
    }

    private static final Random rnd = new Random();
    private List<item> mItems;
    private final Realm realm = Realm.getDefaultInstance();

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler_item, parent, false);
        View hover = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler_item_upper_blur, null);
        return new itemViewHolder(v, hover);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
        //배경은 흰색으로 고정
        holder.mProgressBar.setProgressBackgroundColor(Color.parseColor("#FFFFFF"));

        //Adapter에 적용된 ArrayList에서 순차적으로 꺼내와서 실제 객체에 적용
        holder.mProgressBar.setProgressColor(mItems.get(position).progressColor);
        holder.mProgressBar.setMax(mItems.get(position).maxHour);

        /*
        TODO 원래는 currDay 를 넣어야 하지만 BackGroundColor 확인을 위해 랜덤값.
        이 값은 onPause 이하로 라이프사이클이 내려갈때 다시 갱신되더라.*/
        holder.mProgressBar.setProgress(rnd.nextInt(mItems.get(position).maxHour + 1));

        //holder.day.setText((int)holder.mProgressBar.getProgress() + " / " + pItem.maxDay);
        holder.mTextView.setText(mItems.get(position).taskName);

        holder.mBlurLayout.setHoverView(holder.hoverLayer);
        holder.mBlurLayout.addChildAppearAnimator(holder.hoverLayer, R.id.itemImageView, Techniques.Landing);
        holder.mBlurLayout.addChildDisappearAnimator(holder.hoverLayer, R.id.itemImageView, Techniques.TakingOff);
        holder.mBlurLayout.enableZoomBackground(true);
        holder.mBlurLayout.setBlurDuration(1200);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public RecyclerViewAdapter(@NonNull List<item> parentItemList) {
        mItems = parentItemList;
    }


    public void addItem(final String taskName, final int maxHour, final int color){

        realm.executeTransactionAsync(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                item mItem = realm.createObject(item.class);

                //Primary key increment
                mItem.id = realm.where(item.class).max("id").intValue() + 1;
                mItem.taskName = taskName;
                mItem.maxHour = maxHour;
                //mItem.setCurrHour(0);
                mItem.progressColor = color;
            }
        }, new Realm.Transaction.OnSuccess(){
            @Override
            public void onSuccess() {
                //정상적으로 Item이 삽입된 경우
                notifyItemInserted(mItems.size() - 1);
            }
        });
    }

    @Override
    public void setDataInit() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                notifyItemRangeRemoved(0, mItems.size());
                realm.deleteAll();
            }
        });
    }
}
