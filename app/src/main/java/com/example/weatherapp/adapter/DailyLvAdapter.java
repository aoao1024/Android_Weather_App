package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.bean.DailyResponse;
import com.example.weatherapp.util.WeatherUtil;

import java.util.List;

public class DailyLvAdapter extends BaseAdapter {

    private List<DailyResponse.DailyBean> dailyBeans;
    private Context context;

    public DailyLvAdapter(Context context,List<DailyResponse.DailyBean> dailyBeans) {
        this.context = context;
        this.dailyBeans = dailyBeans;
    }

    @Override
    public int getCount() {
        return dailyBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return dailyBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view==null){
            holder = new ViewHolder();
            //将xml文件转换成view
            view = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast_list,
                viewGroup,false);

            holder.tvDate= view.findViewById(R.id.tv_date);
            holder.tvInfo = view.findViewById(R.id.tv_info);
            holder.tvTempHeight = view.findViewById(R.id.tv_temp_height);
            holder.tvTempLow = view.findViewById(R.id.tv_temp_low);
            holder.ivWeatherState = view.findViewById(R.id.iv_weather_state);

            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        DailyResponse.DailyBean dailyBean = dailyBeans.get(i);
        holder.tvDate.setText(dailyBean.getFxDate());
        holder.tvInfo.setText(dailyBean.getTextDay());

        System.out.println(dailyBean.getTempMax());

        int icon = Integer.parseInt(dailyBean.getIconDay());
        WeatherUtil.changeIcon(holder.ivWeatherState,icon);
        holder.tvTempHeight.setText(dailyBean.getTempMax());
        holder.tvTempLow.setText(dailyBean.getTempMin());

        return view;

    }
    static class ViewHolder{
        TextView tvDate,tvInfo,tvTempHeight,tvTempLow;
        ImageView ivWeatherState;
    }

}
