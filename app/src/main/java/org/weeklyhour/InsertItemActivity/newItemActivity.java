package org.weeklyhour.InsertItemActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import org.weeklyhour.MainActivity.R;

import java.util.Random;

public class newItemActivity extends AppCompatActivity {
    @Override
    protected void onPause() {
        //뒤로가기 하면 그냥 꺼짐
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        final EditText taskName = (EditText) findViewById(R.id.taskNameEditText);
        final EditText memo = (EditText) findViewById(R.id.memoEditText);

        /*
        * Custom Library - ColorPicker Init
        * */
        Random rnd = new Random();
        final ColorPicker colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
        final SaturationBar sBar = (SaturationBar) findViewById(R.id.saturationbar);
        final ValueBar vBar = (ValueBar) findViewById(R.id.valuebar);
        final OpacityBar oBar = (OpacityBar) findViewById(R.id.opacitybar);

        //채도, 명도 바를 colorPicker에 연결
        colorPicker.addSaturationBar(sBar);
        colorPicker.addValueBar(vBar);
        colorPicker.addOpacityBar(oBar);

        colorPicker.setShowOldCenterColor(false); // 가운데의 색을 하나만 보여줌
        colorPicker.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        //기본 색상은 랜덤


        /*
        * NumberPicker Init
        * */
        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setValue(1);       // initializing
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(40);   // 하루 8시간으로 계산해서 주 40시간을 넘기지 못하게..
        numberPicker.setWrapSelectorWheel(true); // circular

        /*
        * Insert Button Init
        * */
        Button submit = (Button) findViewById(R.id.newItemSubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * data check
                * taskName not null
                * */
                if(taskName.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getBaseContext(), "Please Input taskname", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*
                * RequestCode 0
                * ResultCode 1203
                * transfer parentItem information to MainActivity(RecyclerListFragment)
                * */
                Intent result = new Intent();
                result.putExtra("taskName", taskName.getText().toString());
                result.putExtra("memo", memo.getText().toString());
                result.putExtra("color", colorPicker.getColor());
                result.putExtra("maxDay", numberPicker.getValue());
                setResult(1203, result);
                finish();
            }
        });

    }
}
