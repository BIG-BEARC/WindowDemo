package com.example.yhadmin.windowdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener, View.OnTouchListener
{

    private static final int MY_PERMISSIONS_REQUEST_WINDOW = 1;
    private Button                     mCreateWindowBtn;
    private Button                     mFloatingBtn;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager              mWindowManager;
    private int                        mRawX;
    private int                        mRawY;
    private int                        mX;
    private int                        mY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCreateWindowBtn = findViewById(R.id.btn);
        mCreateWindowBtn.setOnClickListener(this);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public void onClick(View v) {

        if (v == mCreateWindowBtn) {
            if (mFloatingBtn!=null){
                return;
            }
            mFloatingBtn = new Button(this);
            mFloatingBtn.setText("Click me");
            mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                                                           WindowManager.LayoutParams.WRAP_CONTENT,
                                                           0,
                                                           0,
                                                           PixelFormat.TRANSPARENT);

            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL//
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE//
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            mLayoutParams.x = 100;
            mLayoutParams.y = 300;
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            mFloatingBtn.setOnTouchListener(this);
            if (Build.VERSION.SDK_INT >= 23) {
                if (! Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                               Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent,MY_PERMISSIONS_REQUEST_WINDOW);
                }else {

                    mWindowManager.addView(mFloatingBtn, mLayoutParams);
                }
            }else {
                mWindowManager.addView(mFloatingBtn, mLayoutParams);
            }
//            permission();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WINDOW) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        try {
            mWindowManager.removeView(mFloatingBtn);
            mFloatingBtn=null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRawX = (int) event.getRawX();
                mRawY = (int) event.getRawY() - getStatusBarHeight();
                mX = (int) event.getX();
                mY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mRawX = (int) event.getRawX();
                mRawY = (int) event.getRawY() - getStatusBarHeight();
                mLayoutParams.x = mRawX - mX;
                mLayoutParams.y = mRawY - mY;
                mWindowManager.updateViewLayout(mFloatingBtn, mLayoutParams);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return false;
    }

    int statusBarHeight = 0;

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {

        if (statusBarHeight == 0) {
            try {
                Class<?> c     = Class.forName("com.android.internal.R$dimen");
                Object   o     = c.newInstance();
                Field    field = c.getField("status_bar_height");
                int      x     = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
