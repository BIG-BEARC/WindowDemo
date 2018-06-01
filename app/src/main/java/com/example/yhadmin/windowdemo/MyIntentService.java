package com.example.yhadmin.windowdemo;

/*
 *  @项目名：  WindowDemo 
 *  @包名：    com.example.yhadmin.windowdemo
 *  @文件名:   MyIntentService
 *  @创建者:   YHAdmin
 *  @创建时间:  2018/5/31 16:52
 *  @描述：    TODO
 */

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *

     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
