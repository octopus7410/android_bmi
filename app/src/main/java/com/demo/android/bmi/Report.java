package com.demo.android.bmi;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Report extends AppCompatActivity {
    private static final String TAG = "LifeCycle";
    TextView textView1, textView2;
    DialogInterface.OnClickListener DialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {   //第二個參數決定是哪個按鈕
            if (which == DialogInterface.BUTTON_POSITIVE)
                Toast.makeText(Report.this, "按下\"確認\"按鈕", Toast.LENGTH_SHORT).show();  //按下按鈕後動作
            else if (which == DialogInterface.BUTTON_NEGATIVE)
                Toast.makeText(Report.this, "按下\"取消\"按鈕", Toast.LENGTH_SHORT).show();
            else if (which == DialogInterface.BUTTON_NEUTRAL) {
                //Uri uri = Uri.parse("https://www.google.com.tw/"); //連接網站
                //Uri uri = Uri.parse("geo:24.18,121.2");
                Uri uri = Uri.parse("tel:0979832703");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(Report.this, "按下\"其他\"按鈕", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStart() {
        Log.i(TAG, "Report-onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Report-onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Report-onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "Report-onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Report-onResume()");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Report-onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        textView1 = (TextView) findViewById(R.id.textView5);
        textView2 = (TextView) findViewById(R.id.textView4);

        Bundle bundle = this.getIntent().getExtras();

        Double hight = bundle.getDouble("KEY_HEIGHT", 0);
        Double weight = bundle.getDouble("KEY_WEIGHT", 0);

        double bmi = weight / (hight * hight) * 10000;
        DecimalFormat df = new DecimalFormat("#.##");
        String bmi2 = df.format(bmi);
        textView1.setText("你的BMI數值=" + bmi2);
        if (bmi < 18.5)
            textView2.setText(R.string.bmi_1);
        else if (18.5 <= bmi && bmi < 24)
            textView2.setText(R.string.bmi_2);
        else if (24 <= bmi && bmi < 27)
            textView2.setText(R.string.bmi_3);
        else if (27 <= bmi && bmi < 30)
            textView2.setText(R.string.bmi_4);
        else if (30 <= bmi && bmi < 35)
            textView2.setText(R.string.bmi_5);
        else if (bmi >= 35)
            textView2.setText(R.string.bmi_6);
        showNotification(bmi);
        // openOptionsDialog();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        //選單
        contextMenuItem(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //選項處裡
        onConItem(item);
        return super.onOptionsItemSelected(item);
    }

    private void contextMenuItem(Menu menu) {
        menu.add(0, 10, 0, "關於").setIcon(android.R.drawable.ic_menu_help).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 20, 0, "結束").setIcon(android.R.drawable.ic_menu_close_clear_cancel).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 30, 0, "首頁").setIcon(android.R.drawable.ic_menu_myplaces).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 40, 0, "上一頁").setIcon(android.R.drawable.ic_menu_zoom).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 40, 0, "上一頁");
    }

    private void onConItem(MenuItem item) {
        switch (item.getItemId()) {
            case 10:
                openOptionsDialog();
                break;
            case 20:
                finish();
                break;
            case 30:
                Uri uri = Uri.parse("https://www.google.com.tw/"); //連接網站
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case 40:
                finish();
                break;

        }
    }

    private void openOptionsDialog() {

        new AlertDialog.Builder(Report.this)   //物件建立
                .setTitle("關於 Android BMI")
                .setMessage("Android BMI Calc")
                .setPositiveButton("確認", DialogListener)
                .setNegativeButton("取消", DialogListener)
                .setNeutralButton("其他", DialogListener)
                .show();    //有傳回值才可以這樣串接
    }

    //API 16以上建議使用
    protected void showNotification(double Report) {
        NotificationManager barManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, Bmi.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification barMsg = new Notification.Builder(this)
                // 通知訊息在狀態列的文字
                .setTicker("歐，你過重囉！")
                // 通知訊息在訊息面板的標題
                .setContentTitle("您的 BMI 值過高")
                // 通知訊息在訊息面板的內容文字
                .setContentText("通知監督人")
                // 通知訊息的圖示
                .setSmallIcon(R.drawable.a002)
                // 點擊訊息面版後會自動移除狀態列上的通知訊息
                .setAutoCancel(true)
                // 等待使用者向下撥動狀態列後點擊訊息面版上的訊息才會開啟指定Activity的畫面
                .setContentIntent(pendingIntent)
                // API Level 16開始支援build()，並建議不要使用getNotification()
                .build();
        barManager.notify(0, barMsg);
    }
}
