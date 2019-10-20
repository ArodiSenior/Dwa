package com.dwakenya.homecarehub.newdwa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Reset extends AppCompatActivity {
    EditText editText, confirm;
    Button button;
    String NEW_URL = "https://techsavanna.net:8181/dwa/newpassword.php";
    String emaill;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    
        SharedPreferences sharedPreferences = getSharedPreferences("ForgotEmail", Context.MODE_PRIVATE);
        emaill = sharedPreferences.getString("email", "");
        
        editText = findViewById(R.id.NewPassword);
        confirm = findViewById(R.id.ConfirmPassword);
        button = findViewById(R.id.Passwordbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editText.getText().toString();
                String cpassword = confirm.getText().toString();
                if (password.isEmpty() || password.length()<6){
                    Toast.makeText(Reset.this, "Please enter at least 6 characters", Toast.LENGTH_SHORT).show();
                    
                }else if (!password.equals(cpassword)){
                    Toast.makeText(Reset.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }else {
                    submitData();
                }
        
            }
        });
    }
    
    public void submitData(){
    
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Submitting password..." );
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NEW_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println("responce"+ response);
                        try {
                        
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                        
                            if (success.equals("1")){
                                Toast.makeText(Reset.this, "Password reset successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Reset.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Reset.this, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Reset.this, "Error occured", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    
                    
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Reset.this, "Error occured", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                System.out.println("error"+ error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String password = editText.getText().toString();
                Map <String, String> map = new HashMap<>();
                map.put("password", password);
                map.put("email", emaill);
                return map;
            }
        };
    
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        
    }
}
