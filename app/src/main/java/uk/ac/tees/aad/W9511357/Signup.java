package uk.ac.tees.aad.W9511357;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup = findViewById(R.id.signupBtn);

        final EditText name = findViewById(R.id.SinputName);
        final  EditText email = findViewById(R.id.SinputEmail);
        final EditText password = findViewById(R.id.SinputPassword);
        final EditText mobile = findViewById(R.id.SinputMobile);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(name.getText().toString(),email.getText().toString(),mobile.getText().toString(),password.getText().toString());
            }
        });

    }
    private static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWRD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        pattern = Pattern.compile(PASSWRD_PATTERN);
        matcher = pattern.matcher(password);
        return password.length() >= 8 && matcher.matches();
    }
    private void register(String name,String email, String mobile,String password)
    {
        if(isValidPassword(password)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    "http://3.137.148.135/register?email=" + email + "&password=" + password + "&mobile=" + mobile + "&name=" + name, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.equals("")) {
                        Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Wrong Inputs", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(request);
        }else{
            Toast.makeText(getApplicationContext(),"Password must be atleast 8 characters long. It must have an alphabet, special character and a number",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
        super.onBackPressed();
    }
}
