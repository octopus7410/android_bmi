package com.demo.android.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Bmi extends AppCompatActivity {
    public static final String PREF = "BMI_PREF";
    public static final String PREF_HEIGHT = "BMI_HEIGHT";
    public static final String PREF_WEIGHT = "BMI_WEIGHT";
    private static final int SOUND_COUNT = 2;
    private static final String TAG_bmi = "BMI";
    private static final String TAG = "LifeCycle";
    EditText et_a01, et_a02;
    TextView et_bmiout1, et_bmiout2;
    Button button;
    TextView tvH2, tvH3;
    LinearLayout layout1;
    ImageView imageView1;
    int count = 0;
    DialogInterface.OnClickListener DialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {   //第二個參數決定是哪個按鈕
            if (which == DialogInterface.BUTTON_POSITIVE)
                Toast.makeText(Bmi.this, "按下\"確認\"按鈕", Toast.LENGTH_SHORT).show();  //按下按鈕後動作
            else if (which == DialogInterface.BUTTON_NEGATIVE)
                Toast.makeText(Bmi.this, "按下\"取消\"按鈕", Toast.LENGTH_SHORT).show();
            else if (which == DialogInterface.BUTTON_NEUTRAL) {
                //Uri uri = Uri.parse("https://www.google.com.tw/"); //連接網站
                //Uri uri = Uri.parse("geo:24.18,121.2");
                Uri uri = Uri.parse("tel:0979832703");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(Bmi.this, "按下\"其他\"按鈕", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private View.OnClickListener papapala = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count++;
            switch (count) {
                case 1: {
                    tvH2.setVisibility(View.VISIBLE);
                    break;
                }
                case 2: {
                    tvH3.setVisibility(View.VISIBLE);
                    break;
                }
                case 3: {
                    layout1.setVisibility(View.GONE);
                    break;
                }
                default: {
                    Toast.makeText(Bmi.this, "幹你娘再點啊!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private int sneezeId;
    private SoundPool soundPool;
    private View.OnClickListener calcBmi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            soundPool.play(sneezeId, 1, 1, 1, 0, 1);
            try {
                if (((et_a01.getText().toString()).equals("")) || ((et_a02.getText().toString()).equals(""))) {
                    Toast.makeText(Bmi.this, "請輸入數值", Toast.LENGTH_SHORT).show();
                    ////////切葉面
                    //chmod();

                } else {

                   /* double b_a01 = Double.parseDouble(et_a01.getText().toString());
                    double b_a02 = Double.parseDouble(et_a02.getText().toString());
                    double bmi = b_a02 / (b_a01 * b_a01) * 10000;
                    DecimalFormat df = new DecimalFormat("#.##");
                    String bmi2 = df.format(bmi);
                    et_bmiout1.setText("你的BMI數值=" + bmi2);
                    if (bmi < 18.5)
                        et_bmiout2.setText(R.string.bmi_1);
                    else if (18.5 <= bmi && bmi < 24)
                        et_bmiout2.setText(R.string.bmi_2);
                    else if (24 <= bmi && bmi < 27)
                        et_bmiout2.setText(R.string.bmi_3);
                    else if (27 <= bmi && bmi < 30)
                        et_bmiout2.setText(R.string.bmi_4);
                    else if (30 <= bmi && bmi < 35)
                        et_bmiout2.setText(R.string.bmi_5);
                    else if (bmi >= 35)
                        et_bmiout2.setText(R.string.bmi_6);
                    //openOptionsDialog();
                    ////////切葉面
                    chmod();
                    reportActivity();*/

                    Intent intent = new Intent();
                    intent.setClass(Bmi.this, Report.class);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("KEY_WEIGHT", Double.parseDouble(et_a02.getText().toString()));
                    bundle.putDouble("KEY_HEIGHT", Double.parseDouble(et_a01.getText().toString()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            } catch (Exception obj) {
                Toast.makeText(Bmi.this, "請輸入數值", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStart() {
        Log.d(TAG, "Bmi-onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Bmi-onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Bmi-onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Bmi-onPause()");
        super.onPause();
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        settings.edit().putString(PREF_HEIGHT, et_a01.getText().toString()).apply();
        settings.edit().putString(PREF_WEIGHT, et_a02.getText().toString()).apply();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Bmi-onResume()");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Bmi-onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        soundPool = new SoundPool(SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        sneezeId = soundPool.load(this, R.raw.autal, 1);

        findViews();
        setickListener();
        registerForContextMenu(button);
        restorePrefs();
        playViewAnimation();
    }
    /*
    //Handler hd = new Handler();
    void openOptionsDialog2() {
        final ProgressDialog pd = new ProgressDialog(Bmi.this);
        pd.setTitle("處理中");
        pd.setMessage("等等");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
        //final ProgressDialog pd =ProgressDialog.show(Bmi.this,"處理中","等等");
        Thread thread = new Thread() {
            @Override
            public void run() {
                int count = 0;
                try {
                    while (count <= 100) {
                        Thread.sleep(40);
                        // hd.post(new Runnable){
                        //    @Override
                                public
                        pd.setProgress(count);
                        //  }
                        count += 1;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        };
        thread.start();
    }*/

    private void findViews() {
        et_a01 = (EditText) findViewById(R.id.editText1);
        et_a02 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
    /*    et_bmiout1 = (TextView) findViewById(R.id.textView3);
        et_bmiout2 = (TextView) findViewById(R.id.textView8);*/
        imageView1 = (ImageView) findViewById(R.id.imageView3);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        tvH2 = (TextView) findViewById(R.id.textView9);
        tvH3 = (TextView) findViewById(R.id.textView7);
    }

    private void setickListener() {

        button.setOnClickListener(calcBmi);
        layout1.setOnClickListener(papapala);
    }

    private void openOptionsDialog() {

        new AlertDialog.Builder(Bmi.this)   //物件建立
                .setTitle("關於 Android BMI")
                .setMessage("Android BMI Calc")
                .setPositiveButton("確認", DialogListener)
                .setNegativeButton("取消", DialogListener)
                .setNeutralButton("其他", DialogListener)
                .show();    //有傳回值才可以這樣串接
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        contextMenuItem(menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onConItem(item);
        return super.onContextItemSelected(item);
    }

    private void contextMenuItem(Menu menu) {
        menu.add(0, 10, 0, "關於").setIcon(android.R.drawable.ic_menu_help).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 20, 0, "結束").setIcon(android.R.drawable.ic_menu_close_clear_cancel).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 30, 0, "首頁").setIcon(android.R.drawable.ic_menu_myplaces).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 40, 0, "下一頁").setIcon(android.R.drawable.ic_menu_zoom).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 40, 0, "下一頁");
        SubMenu subMenu = menu.addSubMenu(0, 50, 0, "子選單");
        subMenu.add(0, 410, 0, "子選項1");
        subMenu.add(0, 420, 0, "子選項2");
        subMenu.add(0, 430, 0, "子選項3");
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
                chmod();
                break;
            case 410:
            case 420:
            case 430:
                Toast.makeText(Bmi.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void chmod() {
        Intent intent = new Intent();
        intent.setClass(Bmi.this, Report.class);
        startActivity(intent);
    }

    private void restorePrefs() {
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        String pref_height = settings.getString(PREF_HEIGHT, "");
        String pref_weight = settings.getString(PREF_WEIGHT, "");
        if (!"".equals(pref_height)) {
            et_a01.setText(pref_height);

            //et_a02.requestFocus();
        }
        if (!"".equals(pref_weight)) {

            et_a02.setText(pref_weight);
            //et_a02.requestFocus();
        }
    }

    private void playViewAnimation() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageView1.setAnimation(anim);

    }
}



