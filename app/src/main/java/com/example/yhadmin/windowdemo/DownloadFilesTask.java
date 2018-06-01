package com.example.yhadmin.windowdemo;

/*
 *  @项目名：  WindowDemo 
 *  @包名：    com.example.yhadmin.windowdemo
 *  @文件名:   DownloadFilesTask
 *  @创建者:   YHAdmin
 *  @创建时间:  2018/5/31 14:33
 *  @描述：    TODO
 */

import android.os.AsyncTask;

import java.net.URL;

public class DownloadFilesTask
        extends AsyncTask<URL, Integer, Long>
{

    @Override
    protected Long doInBackground(URL... urls) {
        int  count     = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
//            totalSize += Downloader.downloadFile(urls[i]);
            publishProgress((int) (i / (float) count) * 100);
            if (isCancelled()) {
                break;
            }
        }
        return totalSize;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
//        setProgressPercent(progress[0]);
    }

    @Override
    protected void onPostExecute(Long result) {
//        showDialog("Downloaded" + result + "bttes");
    }
}
