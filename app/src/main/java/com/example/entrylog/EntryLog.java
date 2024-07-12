package com.example.entrylog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EntryLog extends AppCompatActivity {
    AppCompatButton b2,b1;
    EditText ed1,ed2,ed3,ed4;
    String apiurl= "http://10.0.4.16:3000/api/students";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entry_log);
        b2=(AppCompatButton) findViewById(R.id.back);
        b1=(AppCompatButton) findViewById(R.id.add);
        ed1=(EditText)findViewById(R.id.ed1);
        ed2=(EditText)findViewById(R.id.ed2);
        ed3=(EditText)findViewById(R.id.ed3);
        ed4=(EditText)findViewById(R.id.ed4);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref= getSharedPreferences("logg",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2,s3,s4;
                s1=ed1.getText().toString();
                s2=ed2.getText().toString();
                s3=ed3.getText().toString();
                s4=ed4.getText().toString();
                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals(""))
                    Toast.makeText(getApplicationContext(),"enter all details",Toast.LENGTH_SHORT).show();
                else {
                    //Toast.makeText(getApplicationContext(),"entry has been added",Toast.LENGTH_SHORT).show();
                    JSONObject student = new JSONObject();

                    try {
                        student.put("name",s2);
                        student.put("admission_number",s1);
                        student.put("system_number",s3);
                        student.put("department",s4);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    //JSON Object request creation
                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            apiurl,
                            student,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(), "added successfully", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                    //request queue
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
            }
        });
    }
}