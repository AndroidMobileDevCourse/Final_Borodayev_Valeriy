package com.example.valerijborodaev.authexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProfileActivity extends Activity{

    private TextView username;
    private TextView fname;
    private TextView lname;
    private TextView email;
    private Button logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.usernameView);
        fname = findViewById(R.id.fnameView);
        lname = findViewById(R.id.lnameView);
        email = findViewById(R.id.emailView);
        logoutBtn = findViewById(R.id.logout);
        String data = getIntent().getStringExtra("data");
        try {
            JSONObject dataJson = new JSONObject(data);
            username.setText(dataJson.getString("username"));
            fname.setText(dataJson.getString("fname"));
            lname.setText(dataJson.getString("lname"));
            email.setText(dataJson.getString("email"));

            logoutBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://31.148.99.183:8090/logout")
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        Log.i("dscscsdc", response.body().string());
                        if(response.code() == 200){
                            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
