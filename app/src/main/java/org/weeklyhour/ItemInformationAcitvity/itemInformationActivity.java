package org.weeklyhour.ItemInformationAcitvity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.weeklyhour.MainActivity.R;

public class itemInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_information_item);
    }
}
