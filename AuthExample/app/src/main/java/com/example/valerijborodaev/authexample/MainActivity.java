package com.example.valerijborodaev.authexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Button signupBtn;
    private Button loginBtn;
    private AutoCompleteTextView usernameText;
    private AutoCompleteTextView passwordText;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        errorText = findViewById(R.id.errorText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String username = usernameText.getText().toString();
                    String password = passwordText.getText().toString();

                    JSONObject json = new JSONObject();
                    json.put("username", username);
                    json.put("password", password);
                    RequestBody body = RequestBody.create(JSON, String.valueOf(json));

                    Request request = new Request.Builder()
                            .url("http://31.148.99.183:8090/login")
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();

                    if(response.code() == 200){
                        String responseData = response.body().string();
                        Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("data", responseData);
                        startActivity(profileIntent);
                    } else {
                        String responseData = response.body().string();
                        errorText.setText(responseData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        signupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });
    }
}
