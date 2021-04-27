
package uk.ac.tees.aad.W9511357;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {

               SharedPreferences sharedPreferences =getSharedPreferences("user", Context.MODE_PRIVATE);
               if(sharedPreferences.getString("isLogin","no").equals("no")) {
                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
               }else{
                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
               }

           }
       },3000);
    }
}
