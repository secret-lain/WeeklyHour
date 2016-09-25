package org.weeklyhour.ItemInformationAcitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.EditText;

import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.item;
import org.weeklyhour.MainActivity.R;

import io.realm.Realm;

public class itemInformationActivity extends AppCompatActivity {
    private Realm realm;
    private item targetItem;
    private EditText mTaskName;
    private EditText mHour;
    private EditText mMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_information_item);
        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        int itemID = intent.getIntExtra("itemID", -1);
        targetItem = realm.where(item.class)
                .equalTo("id",itemID).findFirst();

        mTaskName = (EditText) findViewById(R.id.taskNameEditText_info);
        mHour = (EditText) findViewById(R.id.hourEditText_info);
        mMemo = (EditText) findViewById(R.id.memoEditText_info);

        mTaskName.setText(targetItem.taskName);
        mHour.setText(targetItem.currHour + " / " + targetItem.maxHour);
        mMemo.setText(targetItem.memo);


    }


}
