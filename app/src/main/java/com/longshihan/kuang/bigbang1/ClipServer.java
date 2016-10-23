package com.longshihan.kuang.bigbang1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.longshihan.sqlite.Clip;

import java.text.DateFormat;
import java.util.Date;

public class ClipServer extends Service {
    private NotificationManager manager;
    NotificationCompat.Builder notifyBuilder;
    public ClipServer() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService
                (CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(() -> {
            if (clipboardManager.hasPrimaryClip()) {
                if (clipboardManager.getPrimaryClipDescription().hasMimeType(
                        ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    ClipData primaryClip = clipboardManager.getPrimaryClip();
                    if (primaryClip != null) {
                        ClipData.Item item = primaryClip.getItemAt(0);
                        if (item != null && !TextUtils.isEmpty(item.getText().toString())) {
                            startNofi(item.getText().toString());
                            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
                            String comment = primaryClip.getDescription().toString();
                            Clip note = new Clip(null, item.getText().toString(), comment, new Date());
                            App.getInstance().App_session().getClipDao().insert(note);
                        }
                    }
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);

    }

    private void startNofi(String s) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(this, ReceiverClip.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        resultIntent.putExtra(ReceiverClip.EXTRA_TEXT, s);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        notifyBuilder = new NotificationCompat.Builder(this)
            /*设置large icon*/
                .setLargeIcon(bitmap)
             /*设置small icon*/
                .setSmallIcon(R.mipmap.ic_launcher)
            /*设置title*/
                .setContentTitle(getResources().getText(R.string.nofi_tongzhi))
            /*设置详细文本*/
                .setContentText(getResources().getText(R.string.nofi_context))
             /*设置发出通知的时间为发出通知时的系统时间*/
                .setWhen(System.currentTimeMillis())
             /*设置发出通知时在status bar进行提醒*/
                .setTicker("Big bang")
            /*setOngoing(boolean)设为true,notification将无法通过左右滑动的方式清除
            * 可用于添加常驻通知，必须调用cancle方法来清除
            */
                .setOngoing(true)
             /*设置点击后通知消失*/
                .setAutoCancel(true)
             /*设置通知数量的显示类似于QQ那种，用于同志的合并*/
                .setNumber(2)
             /*点击跳转到MainActivity*/
                .setContentIntent(pendingIntent);
        manager.notify(121, notifyBuilder.build());
    }
}
