package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;

import org.weeklyhour.ItemInformationAcitvity.itemInformationActivity;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.item;
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
        public ImageView deleteImageView;
        public ImageView startImageView;
        public ImageView detailImageView;

        public itemViewHolder(View v, View hover) {
            super(v);

            mBlurLayout = (BlurLayout) v.findViewById(R.id.itemBlurLayout);
            mTextView = (TextView) v.findViewById(R.id.itemTextView);
            mProgressBar = (RoundCornerProgressBar) v.findViewById(R.id.itemRoundCornerProgressBar);

            hoverLayer = hover;
            deleteImageView = (ImageView) hoverLayer.findViewById(R.id.deleteImageView);
            startImageView = (ImageView) hoverLayer.findViewById(R.id.startImageView);
            detailImageView = (ImageView) hoverLayer.findViewById(R.id.moreImageView);
        }
    }

    private static final Random rnd = new Random();
    private List<item> mItems;
    private final Realm realm = Realm.getDefaultInstance();
    private Context mContext;

    public RecyclerViewAdapter(@NonNull List<item> parentItemList, Context context) {
        mContext = context;
        mItems = parentItemList;
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler_item, parent, false);
        View hover = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler_item_onclick, null);
        return new itemViewHolder(v, hover);
    }

    @Override
    public void onBindViewHolder(final itemViewHolder holder, final int position) {
        //배경은 흰색으로 고정
        holder.mProgressBar.setProgressBackgroundColor(Color.parseColor("#FFFFFF"));

        //Adapter에 적용된 ArrayList에서 순차적으로 꺼내와서 실제 객체에 적용
        holder.mProgressBar.setProgressColor(mItems.get(position).progressColor);
        holder.mProgressBar.setMax(mItems.get(position).maxHour);

        /*
        TODO 원래는 currDay 를 넣어야 하지만 BackGroundColor 확인을 위해 랜덤값.
        이 값은 onPause 이하로 라이프사이클이 내려갈때 다시 갱신되더라.*/
        holder.mProgressBar.setProgress(rnd.nextInt(mItems.get(position).maxHour + 1));

        holder.mTextView.setText(mItems.get(position).taskName);

        holder.mBlurLayout.setHoverView(holder.hoverLayer);

        holder.mBlurLayout.addChildAppearAnimator(holder.hoverLayer, R.id.deleteImageView, Techniques.Landing);
        holder.mBlurLayout.addChildAppearAnimator(holder.hoverLayer, R.id.moreImageView, Techniques.FadeInLeft);
        holder.mBlurLayout.addChildAppearAnimator(holder.hoverLayer, R.id.startImageView, Techniques.FadeInRight);
        holder.mBlurLayout.addChildDisappearAnimator(holder.hoverLayer, R.id.deleteImageView, Techniques.TakingOff);
        holder.mBlurLayout.addChildDisappearAnimator(holder.hoverLayer, R.id.moreImageView, Techniques.TakingOff);
        holder.mBlurLayout.addChildDisappearAnimator(holder.hoverLayer, R.id.startImageView, Techniques.TakingOff);
        holder.mBlurLayout.enableZoomBackground(false);
        holder.mBlurLayout.setBlurDuration(700);


        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title(R.string.delete_title)
                        .content(R.string.delete_content)
                        .positiveText(R.string.delete_agree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(mContext, mContext.getString(R.string.delete_notice), Toast.LENGTH_SHORT).show();
                                holder.mBlurLayout.dismissHover();
                                deleteItem(position);
                            }
                        })
                        .negativeText(R.string.cancel)
                        /*  필요하면 다시 쓰는걸로 한다.
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(mContext, "Delete Negative Button Clicked", Toast.LENGTH_SHORT).show();
                            }
                        })*/
                        .show();
            }
        });
        holder.startImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().equals("startTag")){
                    Toast.makeText(mContext, "Start Button Clicked", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.drawable.pause_48dp);
                    v.setTag("pauseTag");
                }
                else if(v.getTag().equals("pauseTag")){
                    Toast.makeText(mContext, "Pause Button Clicked", Toast.LENGTH_SHORT).show();
                    ((ImageView)v).setImageResource(R.drawable.arrow_right_dark_48dp);
                }
            }
        });
        holder.detailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInfoActivity = new Intent(mContext, itemInformationActivity.class);
                toInfoActivity.putExtra("itemID", mItems.get(position).id);
                mContext.startActivity(toInfoActivity);

                //Toast.makeText(mContext, "Detail Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onViewRecycled(itemViewHolder holder) {
        holder.mBlurLayout.dismissHover();
        super.onViewRecycled(holder);
    }

    public void addItem(final String taskName, final int maxHour, final int color, final String memo){
        realm.executeTransactionAsync(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                item mItem = realm.createObject(item.class);

                //Primary key increment
                mItem.id = realm.where(item.class).max("id").intValue() + 1;
                mItem.taskName = taskName;
                mItem.maxHour = maxHour;
                mItem.currHour = 0;
                mItem.progressColor = color;
                mItem.memo = memo;

            }
        }, new Realm.Transaction.OnSuccess(){
            @Override
            public void onSuccess() {
                //정상적으로 Item이 삽입된 경우
                notifyItemInserted(mItems.size() - 1);
            }
        });
    }

    public void deleteItem(final int position){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                item target = mItems.get(position);
                notifyItemRemoved(position);
                target.deleteFromRealm();
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
