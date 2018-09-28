package com.qr.livedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class JobTest extends JobService {



    @Override
    public boolean onStartJob(JobParameters params) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ShowNotification(format.format(new Date()));
        ToStartJob();
        Log.i("12", "onStartJob: ");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
    private void ToStartJob(){
        JobInfo.Builder builder = new JobInfo.Builder(5,new ComponentName(this,JobTest.class));
        builder.setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(20000));
        builder.setOverrideDeadline(TimeUnit.MILLISECONDS.toMillis(20000));
        builder.setRequiresCharging(false);
        builder.setPersisted(true);
        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
    private void ShowNotification(String content){
        //Log.d("ss", "ShowNotification: ");
        String cId = "hr1";
        String cName = "系统通知1";
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(cId,cName,NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notification = new Notification.Builder(getBaseContext(),cId)
                    .setChannelId(cId)
                    .setContentTitle("新通知")
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.logo481)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo96))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
        }else{
            notification = new NotificationCompat.Builder(this,cId)
                    .setContentTitle("新通知")
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.logo481)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo96))
                    .setContentIntent(pendingIntent)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .build();
        }
        notificationManager.notify(1,notification);
        //startForeground(1,notification);
    }
}
