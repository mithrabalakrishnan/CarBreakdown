package uk.ac.tees.aad.W9511357;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView email = findViewById(R.id.emailInput);
        final TextView password = findViewById(R.id.passwordInput);
        Button btn =  findViewById(R.id.loginBtn);
        Button btn2 = findViewById(R.id.signupBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin(email.getText().toString(),password.getText().toString());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Signup.class));
            }
        });

    }
    private void requestLogin(String email, String password){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest request  = new StringRequest(Request.Method.GET, "http://3.137.148.135/login?email="+email+"&password="+password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("")){
                    JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

                    SharedPreferences sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("isLogin","yes");
                    editor.putString("name",jsonObject.get("name").getAsString());
                    editor.putString("email",jsonObject.get("email").getAsString());
                    editor.putString("mobile",jsonObject.get("mobile").getAsString());
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }else
                {
                    Toast.makeText(getApplicationContext(),"Wrong Details",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
        super.onBackPressed();
    }
}
