package com.example.weatherapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.weatherapp.adapter.DailyLvAdapter;
import com.example.weatherapp.bean.DailyResponse;
import com.example.weatherapp.bean.LocationResponse;
import com.example.weatherapp.bean.NowResponse;
//import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.util.WeatherUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//和风天气key：ffc54b8ad4d74106bb8fcca48844a009
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvLocation,tvTemp,tvText,tvDate, tvMaxMin,tvFeelsLike,tvUpdateTime,tvHumidity,tvWindDir,tvWindScale,tvVis,tvPressure,tvCloud;
    private ImageView ivWeather;
    private ImageButton ibLocation,ibList,ib_music,ibSchedule;

    private ListView lvWeather;
    private List<DailyResponse.DailyBean> dailyBeans = new ArrayList<>();
    private DailyLvAdapter dailyLvAdapter;

    //定位客户端
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //询问获取定位权限
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        },1);

        initView();

        String district = "浑南区";
        //searchLocationId(district) 获取locationId，并调用searchNow(locationId)获取实时天气信息
        //searchNow(locationId) 搜索实时天气,并调用updateNow(Now)将实时天气信息显示到页面中
        //网络请求要在子线程中进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchLocationId(district);
            }
        }).start();

        //添加点击事件
        ib_music.setOnClickListener(this);
        ibLocation.setOnClickListener(this);
        tvLocation.setOnClickListener(this);

        lvWeather.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailInfoActivity.class);
                intent.putExtra("fxDate",dailyBeans.get(i).getFxDate());
                intent.putExtra("tempMax",dailyBeans.get(i).getTempMax());
                intent.putExtra("tempMin",dailyBeans.get(i).getTempMin());
                intent.putExtra("iconDay",dailyBeans.get(i).getIconDay());
                intent.putExtra("textDay",dailyBeans.get(i).getTextDay());
                intent.putExtra("windDirDay",dailyBeans.get(i).getWindDirDay());
                intent.putExtra("windScaleDay",dailyBeans.get(i).getWindScaleDay());
                intent.putExtra("precip",dailyBeans.get(i).getPrecip());
                intent.putExtra("humidity",dailyBeans.get(i).getHumidity());
                intent.putExtra("pressure",dailyBeans.get(i).getPressure());
                intent.putExtra("vis",dailyBeans.get(i).getVis());
//                intent.putExtra("cloud",dailyBeans.get(i).getCloud());
                startActivity(intent);
            }
        });

    }

    private void initView() {
        tvLocation = findViewById(R.id.tv_location);
        tvTemp = findViewById(R.id.tv_temp);
        tvText = findViewById(R.id.tv_text);
        tvDate = findViewById(R.id.tv_date);
        tvMaxMin = findViewById(R.id.tv_max_min);
        tvUpdateTime = findViewById(R.id.tv_updateTime);
        tvFeelsLike = findViewById(R.id.tv_feelsLike);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWindDir = findViewById(R.id.tv_windDir);
        tvWindScale = findViewById(R.id.tv_windScale);
        tvVis = findViewById(R.id.tv_vis);
        tvPressure = findViewById(R.id.tv_pressure);
        tvCloud =  findViewById(R.id.tv_cloud);
        ibLocation = findViewById(R.id.ib_location);
        ibList = findViewById(R.id.ib_list);
        ib_music = findViewById(R.id.ib_music);
        ibSchedule = findViewById(R.id.ib_schedule);
        lvWeather = findViewById(R.id.lv_weather);
        ivWeather = findViewById(R.id.iv_weather);

        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int m = cal.get(Calendar.MONTH);
        m++;
        int d1 = cal.get(Calendar.DAY_OF_MONTH);
        int d2 = cal.get(Calendar.DAY_OF_WEEK);
        char [] week = new char[]{'日','一','二','三','四','五','六'};
        String date = m +"月" + d1 + "日-周" + week[d2-1];
        tvDate.setText(date);
    }
    //    定位函数
    private void startLocation() throws Exception {
        initLocation();//定位初始化
        //创建客户实例
        mLocationClient = new LocationClient(getApplicationContext());
        //绑定监听器
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setNeedNewVersionRgc(true);
        mLocationClient.setLocOption(option);
//        option.setScanSpan(5000);//每隔5s刷新一下
        mLocationClient.setLocOption(option);
        mLocationClient.stop();//停止定位
        mLocationClient.start();//开始定位
    }

    private void initLocation() throws Exception {
        //创建客户实例
        mLocationClient = new LocationClient(getApplicationContext());
        //绑定监听器
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setNeedNewVersionRgc(true);
        mLocationClient.setLocOption(option);
//        option.setScanSpan(5000);//每隔5s刷新一下
        mLocationClient.setLocOption(option);
    }
    //定位监听器
    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
        String district = bdLocation.getDistrict();
        System.out.println(district);
        tvLocation.setText(district);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    searchLocationId(district);
                }
            }).start();
        }
    };

    /**
     * 搜索城市信息并获取LocationId
     * 调用searchNow()获取实时天气
     * 调用searchDaily()获取每日天气预报
     * @param district
     */
    private void searchLocationId(String district) {
        //使用Get异步请求
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //拼接访问地址
                .url("https://geoapi.qweather.com/v2/city/lookup?key=ffc54b8ad4d74106bb8fcca48844a009&location="+district)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    System.out.println("错误信息：" + e);
                    Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("a","获取数据成功了");
                    Log.d("a","response.code()="+response.code());
//                    Log.d("a","response.body().string()="+response.body().string());
                    String json = (String) response.body().string();
                    LocationResponse location = new Gson().fromJson(json,LocationResponse.class);
//                    response.close();
                    //获取locationId
                    String locationId = location.getLocation().get(0).getId();
                    Log.d("a","locationId="+locationId);
                    //获取实时天气信息和每日天气预报并显示到界面中
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            searchNow(locationId);
                            searchDaily(locationId);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    finish();
//                                }
//                            });
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * 搜索实时天气
     * 调用updateNow()将实时天气信息显示到页面中
     * @param locationId
     */
    private void searchNow(String locationId){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //拼接访问地址https://devapi.qweather.com/v7/weather/now?location=101010100&key=YOUR_KEY
                .url("https://devapi.qweather.com/v7/weather/now?location="+locationId+"&key=ffc54b8ad4d74106bb8fcca48844a009")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    System.out.println("错误信息：" + e);
                    Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("a","获取数据成功了");
                    Log.d("a","response.code()="+response.code());
//                    Log.d("a","response.body().string()="+response.body().string());
                    String json = (String) response.body().string();
                    NowResponse now = new Gson().fromJson(json,NowResponse.class);
//                    response.close();
                    //将获取的实时天气信息显示到页面中
                    if (now.getNow() != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateNow(now);
                            }
                        });
                    }
                }
            }
            //将获取的实时天气信息显示到页面中
            private void updateNow(NowResponse now) {

                tvTemp.setText(now.getNow().getTemp());
                tvText.setText(now.getNow().getText());
                tvUpdateTime.setText(now.getUpdateTime());
                tvFeelsLike.setText(now.getNow().getFeelsLike());
                tvHumidity.setText(now.getNow().getHumidity());
                tvWindDir.setText(now.getNow().getWindDir());
                tvWindScale.setText(now.getNow().getWindScale());
                tvVis.setText(now.getNow().getVis());
                tvPressure.setText(now.getNow().getPressure());
                tvCloud.setText(now.getNow().getCloud());
                int icon = Integer.parseInt(now.getNow().getIcon());
                WeatherUtil.changeIcon(ivWeather,icon);
            }
        });
    }

    /**
     * 搜索未来7日天气预报
     * 调用updateDaily()将每日天气信息显示到页面中
     * @param locationId
     */
    private void searchDaily(String locationId) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //拼接访问地址https://https://devapi.qweather.com/v7/weather/7d?location=101010100&key=YOUR_KEY
                .url("https://devapi.qweather.com/v7/weather/7d?location="+locationId+"&key=ffc54b8ad4d74106bb8fcca48844a009")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    System.out.println("错误信息：" + e);
                    Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("a","获取数据成功了");
                    Log.d("a","response.code()="+response.code());
//                    Log.d("a","response.body().string()="+response.body().string());
                    String json = (String) response.body().string();
                    DailyResponse daily = new Gson().fromJson(json, DailyResponse.class);
//                    response.close();
                    //将获取的每日天气信息显示到页面中
                    if (daily.getDaily() != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDaily(daily);
                            }
                        });
                    }
                }
            }

            //将获取的每日天气信息显示到页面中
            private void updateDaily(DailyResponse daily) {

                tvMaxMin.setText(daily.getDaily().get(0).getTempMax()+"℃/"+daily.getDaily().get(0).getTempMin()+"℃");
                dailyBeans = daily.getDaily();
                dailyLvAdapter = new DailyLvAdapter(MainActivity.this,dailyBeans);
                lvWeather.setAdapter(dailyLvAdapter);
            }

        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_location:
                try {
                    startLocation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ib_list:

                break;
            case R.id.ib_music:
                Intent intent = new Intent(this,MusicPlayerActivity.class);
//                System.out.println("点击音乐播放器按钮");
                startActivity(intent);
                break;
            case R.id.ib_schedule:

                break;
            case R.id.tv_location:
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this,R.style.MyDialogTheme);
                AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
                builder.setTitle("请输入城市名：");
                final EditText editText = new EditText(MainActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String city = editText.getText().toString();
                        tvLocation.setText(city);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                searchLocationId(city);
                            }
                        }).start();
                    }
                });
                builder.setNegativeButton("取消",null);
                AlertDialog dialog = builder.create();
                dialog.show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.city_dialog, null);
//                builder.setView(dialogView);
//
//                Button positiveButton = dialogView.findViewById(R.id.positive_button);
//                Button negativeButton = dialogView.findViewById(R.id.negative_button);
//                EditText etCity = dialogView.findViewById(R.id.et_city);
//
//                positiveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // 处理确定按钮的点击事件
//                        String city = etCity.getText().toString();
//                        tvLocation.setText(city);
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                searchLocationId(city);
//                            }
//                        }).start();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
                break;
        }

    }

}
