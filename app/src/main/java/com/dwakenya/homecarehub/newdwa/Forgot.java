package com.dwakenya.homecarehub.newdwa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgot extends AppCompatActivity {
    LinearLayout linearLayout;
    EditText editText, code;
    Button button, reset;
    
    String CODE_URL = "https://techsavanna.net:8181/dwa/Code.php";
    String TOKEN_URL = "https://techsavanna.net:8181/dwa/passwordreset.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        linearLayout = findViewById(R.id.LinearLayout);
        editText = findViewById(R.id.ForgotEmail);
        code = findViewById(R.id.ForgotCode);
        button = findViewById(R.id.Forgotbtn);
        reset = findViewById(R.id.Resetbtn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ver = code.getText().toString();
                if (ver.isEmpty()){
                    Toast.makeText(Forgot.this, "Verification code is required", Toast.LENGTH_SHORT).show();
                }else {
                    confirmToken();
                
                }
        
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(Forgot.this, "Email is required", Toast.LENGTH_SHORT).show();
                }else {
                    sendCode();
                    editText.setEnabled(false);
                    linearLayout.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                    Toast.makeText(Forgot.this, "Verification Code sent to the Email specified, The code will expire after 10 minutes", Toast.LENGTH_LONG).show();
    
                }
        
            }
        });
    }
    
    public void sendCode() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CODE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("responce"+ response);
                        
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error"+ error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String email = editText.getText().toString();
                //String LastPhone = phone.length() >= 9 ? phone.substring(phone.length() - 9): "";
                Map <String, String> map = new HashMap<>();
                map.put("email", email);
                return map;
            }
        };
        
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        
        
        
    }
    
    public void confirmToken() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Resetting password..." );
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println("responce"+ response);
                        try {
        
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
        
                            if (success.equals("1")){
                                String email = editText.getText().toString();
                                SharedPreferences sharedPreferences = getSharedPreferences("ForgotEmail", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.apply();
                                startActivity(new Intent(Forgot.this, Reset.class));
                            }else {
                                Toast.makeText(Forgot.this, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Forgot.this, "Error occured", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                      
                        
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Forgot.this, "Error occured", Toast.LENGTH_SHORT).show();
                System.out.println("error"+ error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String ver = code.getText().toString();
                //String LastPhone = phone.length() >= 9 ? phone.substring(phone.length() - 9): "";
                Map <String, String> map = new HashMap<>();
                map.put("code", ver);
                return map;
            }
        };
        
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        
        
        
    }
}
