package com.sigma.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String SIGMA_SERVER_URL = "http://sigma-solutions.eu/test";
    private List<String> synchronizedList = Collections.synchronizedList(new ArrayList<String>());
    private Button btnStart;
    private Button btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runAllBackgroundThread();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAllThread();
            }
        });
    }
    private void runAllBackgroundThread(){
        //Start thread 1 get current GPS
        ScheduledExecutorServiceManager.getmInstance()
                .addNewThreadWithFixedRate(6*60, new Runnable() {
            @Override
            public void run() {
                synchronized (synchronizedList) {
                    synchronizedList.add(GpsUtil.getCurrentGps());
                }
            }
        });
        //Start thread 2 get current battery percentage
        ScheduledExecutorServiceManager.getmInstance()
                .addNewThreadWithFixedRate(9*60, new Runnable() {
            @Override
            public void run() {
                synchronized (synchronizedList) {
                    synchronizedList.add(BatteryUtil.getBatteryPercentage(MainActivity.this)+"");
                }
            }
        });
        //Start thread 3 check and post to server
        ScheduledExecutorServiceManager.getmInstance()
                .addNewThreadWithFixedRate(1, new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = null;
                synchronized (synchronizedList) {
                    if (synchronizedList.size() >5){
                        params = getParams(synchronizedList);
                        synchronizedList.clear();
                    }
                }
                if (params != null){
                    HttpUtil.postToServer(SIGMA_SERVER_URL, params);
                }
            }
        });
    }
    private void stopAllThread(){
        ScheduledExecutorServiceManager.getmInstance().cancelAllTasks();
    }
    private HashMap<String, String> getParams(List<String> datas){
        HashMap<String, String> params = new HashMap<>();
        String content ="";
        for (String item:datas
             ) {
            if (content.length() ==0){
                content+=item;
            }else{
                content+=","+item;
            }
        }
        params.put("data", content);
        return params;
    }

}
