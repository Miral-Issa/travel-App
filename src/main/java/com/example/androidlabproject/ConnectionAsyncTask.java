package com.example.androidlabproject;

import android.app.Activity;
import android.os.AsyncTask;
import java.util.List;
import com.example.androidlabproject.MainActivity;
public class ConnectionAsyncTask extends AsyncTask<String,String,String> {
    Activity activity;
    long startTime = 0;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
        ((MainActivity) activity).setProgress(true);
        startTime = System.currentTimeMillis() % 1000;
    }
    @Override
    protected String doInBackground(String... params) {
        try{
            String data = HttpManager.getData(params[0]);
            if(data == null)
            {
                cancel(true);
                return null;
            }else {
                return data;
            }
//            return data;
        }catch (Exception e)
        {
            //((MainActivity) activity).connectionFailed();
            cancel(true);
            return null;
        }

    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s == null)
        {
            cancel(true);
        }else {
            ((MainActivity) activity).setProgress(false);
            ((MainActivity) activity).setButtonText("connected");
//        List<Student> students =
//                StudentJsonParser.getObjectFromJson(s);
            CatagorieJsonParser.getObjectFromJson(s, activity);
            PropertieJsonParser.getObjectFromJson(s, activity);

            //((MainActivity) activity).fillData();
            ((MainActivity) activity).moveToLoginPage();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        ((MainActivity) activity).setProgress(false);
        ((MainActivity) activity).connectionFailed();
    }
}
