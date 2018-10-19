package com.example.valerijborodaev.authexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.valerijborodaev.authexample.MainActivity.JSON;

public class SignupActivity extends Activity {

    private Button signupBtn;
    private AutoCompleteTextView usernameText;
    private AutoCompleteTextView fnameText;
    private AutoCompleteTextView lnameText;
    private AutoCompleteTextView emailText;
    private AutoCompleteTextView passwordText;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupBtn = findViewById(R.id.signupBtnS);
        fnameText = findViewById(R.id.fnameS);
        lnameText = findViewById(R.id.lnameS);
        emailText = findViewById(R.id.emailS);
        passwordText = findViewById(R.id.passwordS);
        usernameText = findViewById(R.id.usernameS);
        errorText = findViewById(R.id.errorTextS);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String username = usernameText.getText().toString();
                    String password = passwordText.getText().toString();
                    String lname = lnameText.getText().toString();
                    String fname = fnameText.getText().toString();
                    String email = emailText.getText().toString();

                    JSONObject json = new JSONObject();
                    json.put("username", username);
                    json.put("password", password);
                    json.put("lname", lname);
                    json.put("fname", fname);
                    json.put("email", email);

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, String.valueOf(json));

                    Request request = new Request.Builder()
                            .url("http://31.148.99.183:8090/signup")
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();

                    if(response.code() == 200){
                        String responseData = response.body().string();
                        Intent profileIntent = new Intent(SignupActivity.this, ProfileActivity.class);
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
    }
}
