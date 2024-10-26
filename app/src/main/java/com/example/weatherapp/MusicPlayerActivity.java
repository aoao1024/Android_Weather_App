package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ImageButton imageButtonPre,imageButtonNext,imageButtonBack,imageButtonForward,imageButtonPlayOrPause;
    private SeekBar seekBar;
    private RatingBar ratingBar;
    private TextView textView;
    private ListView listView;
    private String mp3List[] = {"AZU - For You.mp3", "Rainton桐 - 最后的旅行（纯歌版）.mp3",
            "STRlighT - 天気の子.mp3","九月橙 - 三葉のテーマ.mp3",
            "李冠霖 - 忽然之间.mp3","林加弦 - 夏天的风（男声 吉他版）.mp3",
            "莫文蔚 - 忽然之间.mp3","蔡健雅 - 达尔文.mp3",
            "那英 - 默.mp3","陈奕迅 - 梦想天空分外蓝.mp3",};
    private int index = 0;//当前正在播放的位置
    private boolean flag = true;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 999 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            play(index);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        initView();//初始化组件
        //动态申请权限
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            play(index);//有权限
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);//没权限，申请权限
        }

        imageButtonPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = index - 1 >= 0 ? index -1 : 0;
                index = idx;
                play(index);
            }
        });
        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = index + 1 > 9 ? 9 : index + 1;
                index = idx;
                stopMusic();
                play(index);
            }
        });
        new MyTask().execute();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MusicPlayerActivity.this, android.R.layout.simple_list_item_1, mp3List);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                play(index);
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000 < 0 ? 0 : mediaPlayer.getCurrentPosition() - 10000);
            }
        });
        imageButtonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000 > mediaPlayer.getDuration() ? 0 : mediaPlayer.getCurrentPosition() + 10000);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(mediaPlayer != null){
                    Log.i("Rating", "" + rating / 10);
                    mediaPlayer.setVolume(rating / 10, rating / 10);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    int duration = mediaPlayer.getDuration();
                    Log.i("Duration", duration + "");
                    Log.i("SeekTo", Math.round(duration * (progress / 100.0f)) + "");
                    mediaPlayer.seekTo(Math.round(duration * (progress / 100.0f)));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        imageButtonPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imageButtonPlayOrPause.setImageResource(android.R.drawable.ic_media_play);
                }else if(mediaPlayer != null && mediaPlayer.isPlaying() == false){
                    mediaPlayer.start();
                    imageButtonPlayOrPause.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

    }

    private void initView() {
        imageButtonNext = findViewById(R.id.imageButtonNext);
        imageButtonPre = findViewById(R.id.imageButtonPre);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonForward = findViewById(R.id.imageButtonForward);
        imageButtonPlayOrPause = findViewById(R.id.imageButtonPlayOrPause);
        imageButtonPlayOrPause.setImageResource(android.R.drawable.ic_media_pause);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setMax(10);
        ratingBar.setNumStars(10);
        ratingBar.setProgress(5);
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
    }
    private void play(int index){
        try {
            mediaPlayer.reset();
            String path = Environment.getExternalStorageDirectory() + File.separator + "Music" + File.separator + mp3List[index];
            Log.i("PATH", path);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            if (mediaPlayer!=null && !mediaPlayer.isPlaying()){
                mediaPlayer.prepare();
                mediaPlayer.start();
                imageButtonPlayOrPause.setImageResource(android.R.drawable.ic_media_pause);
                mediaPlayer.setVolume(0.5f, 0.5f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if(mediaPlayer!=null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            imageButtonPlayOrPause.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    class MyTask extends AsyncTask<String, Integer, Object> {
        //线程执行之前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //线程执行之后
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
        //更新界面seekBar的进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int currentPosition = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            int persent = Math.round((currentPosition + 0.0f) / duration * 100);
            Log.i("Persent", persent + "");
            seekBar.setProgress(persent);
            String text = "文件名：" + mp3List[index]
                    + " 时长：" + duration / 1000 / 60 + "分" + duration / 1000 % 60 + "秒"
                    + " 当前进度：" + currentPosition / 1000 / 60 + "分" + currentPosition / 1000 % 60 + "秒";
            textView.setText(text);
        }
        //run
        @Override
        protected Object doInBackground(String... strings) {
            while(flag){
                try {
                    Thread.sleep(500);//每500ms更新一次进度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress();//调用onProgressUpdate();
            }
            return null;
        }
    }
}