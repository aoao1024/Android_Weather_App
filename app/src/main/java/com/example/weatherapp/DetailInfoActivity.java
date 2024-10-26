package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.util.WeatherUtil;

public class DetailInfoActivity extends AppCompatActivity {

    private TextView tvFcDate,tvTextDay,tvTempMaxMin,tvDirScaleDay,tvPrecip,tvHumidity,tvPressure,tvVis;
    private ImageView ivIconDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        Intent intent = getIntent();

        initView();

        tvFcDate.setText(intent.getStringExtra("fxDate"));
        int icon = Integer.parseInt(intent.getStringExtra("iconDay"));
        WeatherUtil.changeIcon(ivIconDay,icon);

        tvTextDay.setText(intent.getStringExtra("textDay"));

        String tempMaxMin = intent.getStringExtra("tempMax") + "℃ / "
                + intent.getStringExtra("tempMin") + "℃";
        tvTempMaxMin.setText(tempMaxMin);

        String DirScaleDay = intent.getStringExtra("windDirDay")
                + "   " + intent.getStringExtra("windScaleDay") + "级";
        tvDirScaleDay.setText(DirScaleDay);

        String s1 = "降水量   " + intent.getStringExtra("precip") + " mm";
        tvPrecip.setText(s1);

        String s2 = "湿度   " + intent.getStringExtra("humidity") + " %";
        tvHumidity.setText(s2);

        String s3 = "气压   " + intent.getStringExtra("pressure") + " bp";
        tvPressure.setText(s3);

        String s4 = "能见度   " + intent.getStringExtra("vis") + " km";
        tvVis.setText(s4);
    }

    private void initView() {
        tvFcDate = findViewById(R.id.tv_fcDate);
        tvTextDay = findViewById(R.id.tv_textDay);
        tvTempMaxMin = findViewById(R.id.tv_tempMaxMin);
        tvDirScaleDay = findViewById(R.id.tv_dirScaleDay);
        tvPrecip = findViewById(R.id.tv_precip);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvPressure = findViewById(R.id.tv_pressure);
        tvVis = findViewById(R.id.tv_vis);
        ivIconDay = findViewById(R.id.iv_iconDay);
    }
}