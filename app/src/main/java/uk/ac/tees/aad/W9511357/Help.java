package uk.ac.tees.aad.W9511357;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Spinner spinner = findViewById(R.id.spinner2);
        final Spinner spinner2 = findViewById(R.id.spinner3);
        final EditText editText = findViewById(R.id.registration);


        findViewById(R.id.finalHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("location",MODE_PRIVATE);
                float lat = sharedPreferences.getFloat("lat",0.00f);
                float lng = sharedPreferences.getFloat("lng",0.00f);
                sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                String name = sharedPreferences.getString("name","unknown");
                String email = sharedPreferences.getString("email","email");
                String mobile = sharedPreferences.getString("mobile","mobile");
                sharedPreferences = getSharedPreferences("cars",MODE_PRIVATE);
                String car = sharedPreferences.getString("car","car");
                String model = sharedPreferences.getString("model","model");
                String registrationNumber = editText.getText().toString();
                requestHelp(name,email,mobile,lat,lng,car,model,registrationNumber);


            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.brands,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String company = parent.getItemAtPosition(position).toString();

                SharedPreferences sharedPreferences = getSharedPreferences("cars",MODE_PRIVATE);
                SharedPreferences.Editor editer = sharedPreferences.edit();
                editer.putString("car",company);
                editer.apply();
                spinner2.setVisibility(View.VISIBLE);
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://car-stockpile.p.rapidapi.com/models?make="+company)
                        .get()
                        .addHeader("x-rapidapi-key", "1fa334a949msh9f556b25166fa26p1c4e84jsne9afe40ad705")
                        .addHeader("x-rapidapi-host", "car-stockpile.p.rapidapi.com")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String res = response.body().string();
                        Help.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
                                List<CharSequence> charSequenceList = new ArrayList<CharSequence>();

                                for (JsonElement data:jsonObject.get("models").getAsJsonArray()){
                                    charSequenceList.add(data.getAsString().toString());
                                }
                                ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(getApplicationContext(),android.R.layout.simple_spinner_item,charSequenceList);
                                adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter2);

                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String company = parent.getItemAtPosition(position).toString();
                                        SharedPreferences sharedPreferences = getSharedPreferences("cars",MODE_PRIVATE);
                                        SharedPreferences.Editor editer = sharedPreferences.edit();
                                        editer.putString("model",company);
                                        editer.apply();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    private void requestHelp(String name, String email, String mobile, float lat, float lng, String car, String model, final String registrationNumber){
        if(registrationNumber == null || registrationNumber.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please give registration number",Toast.LENGTH_SHORT).show();
        }
        else{
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest request  = new StringRequest(
                com.android.volley.Request.Method.GET,
                "http://3.137.148.135/help?email="+email+"&carBrand="+car+"&mobile="+mobile+"&name="+name+"&carModel="+model+"&lat="+lat+"&lng="+lng, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("")){
                  JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
                  Intent intent = new Intent(getApplicationContext(),Success.class);
                  intent.putExtra("name",jsonObject.get("name").getAsString());
                    intent.putExtra("email",jsonObject.get("email").getAsString());
                    intent.putExtra("mobile",jsonObject.get("mobile").getAsString());
                    intent.putExtra("car",jsonObject.get("carBrand").getAsString());
                    intent.putExtra("brand",jsonObject.get("carModel").getAsString());
                    intent.putExtra("lat",Float.parseFloat(jsonObject.get("lat").getAsString()));
                    intent.putExtra("lng",Float.parseFloat(jsonObject.get("lng").getAsString()));
                    intent.putExtra("number",registrationNumber);
                    startActivity(intent);

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"Wrong Inputs",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);

    }}


}
