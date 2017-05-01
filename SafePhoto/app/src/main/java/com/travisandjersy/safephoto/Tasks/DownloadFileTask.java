package com.travisandjersy.safephoto.Tasks;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by trvslhlt on 4/30/17.
 */


// new DownloadFileFromURL().execute("http://www.example.com/IMG.jpg");
// http://stackoverflow.com/a/29189383/3576925
class DownloadFileTask extends AsyncTask<String, String, String> {

    private static String TAG = "DownloadFileTask";
    private String localFilename;

    public DownloadFileTask(String localFilename) {
        this.localFilename = localFilename;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Starting download");
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            String root = Environment.getExternalStorageDirectory().toString();

            System.out.println("Downloading");
            URL url = new URL(f_url[0]);

            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(root + "/" + localFilename);
            byte data[] = new byte[1024];

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        System.out.println("Downloaded");
    }

}