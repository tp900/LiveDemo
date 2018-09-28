package com.qr.livedemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("12", "onCreate:job1 ");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            ToStartJob();
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private void ToStartJob(){
        Log.i("12", "onCreate:job ");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(this,JobTest.class));
        builder.setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(20000));
        builder.setOverrideDeadline(TimeUnit.MILLISECONDS.toMillis(20000));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING);
        builder.setBackoffCriteria(TimeUnit.MILLISECONDS.toMillis(20000),JobInfo.BACKOFF_POLICY_LINEAR);
        builder.setRequiresCharging(false);
        builder.setPersisted(true);
        jobScheduler.schedule(builder.build());
    }
}
