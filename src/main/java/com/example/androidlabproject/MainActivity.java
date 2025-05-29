package com.example.androidlabproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setProgress(false);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new
                        ConnectionAsyncTask(MainActivity.this);

                try {
                    connectionAsyncTask.execute("https://mocki.io/v1/f208b41b-12f1-45d0-9b74-2a635f184a2d");
                }catch (Exception e)
                {
                    connectionFailed();
                }

            }
        });

        linearLayout = (LinearLayout)
                findViewById(R.id.layout);
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void fillData() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        linearLayout.removeAllViews();

        DataBaseHelper dataBaseHelper =new DataBaseHelper(MainActivity.this,"ProjectDB", null,1);
        Cursor allCCursor = dataBaseHelper.getAllProperties();

        while (allCCursor.moveToNext()){
            TextView textView =new TextView(MainActivity.this);
            textView.setText(
                    "Id= "+allCCursor.getString(0)
                            +"\ntitle= "+allCCursor.getString(1)
                            +"\n\n"
            );
            linearLayout.addView(textView);
        }
    }

    public void moveToLoginPage()
    {
        Intent intent;
        intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void connectionFailed()
    {
        Toast toast =Toast.makeText(MainActivity.this,
                "connection failed",Toast.LENGTH_SHORT);
        toast.show();
    }
}