package com.dwakenya.homecarehub.newdwa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.dwakenya.homecarehub.newdwa.helpers.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    
    EditText update_fname;
    EditText update_lname;
    EditText update_id;
    EditText update_phone;
    EditText update_location;
    EditText update_password;
    EditText update_cpassword;
    Button update_button;
    HashMap<String, String> user;
    
    // Session Manager Class
    SessionManager session;
    
    String UPDATE_URL = "https://techsavanna.net:8181/dwa/Update.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
    
        getSupportActionBar().setTitle("Update Profile");
    
        // Session Manager
        session = new SessionManager(getApplicationContext());
    
        // get user data from session
        user = session.getUserDetails();
    
        update_fname = findViewById(R.id.update_fname);
        update_fname.setText(user.get(SessionManager.KEY_FNAME));
        update_lname = findViewById(R.id.update_lname);
        update_lname.setText(user.get(SessionManager.KEY_LNAME));
        update_id = findViewById(R.id.update_id);
        update_id.setText(user.get(SessionManager.KEY_ID_NO));
        update_phone = findViewById(R.id.update_phone);
        update_phone.setText(user.get(SessionManager.KEY_PHONE));
        update_location = findViewById(R.id.update_location);
        update_location.setText(user.get(SessionManager.KEY_LOCATION));
        update_password = findViewById(R.id.update_password);
        update_password.setText(user.get(SessionManager.KEY_PASSWORD));
        update_cpassword = findViewById(R.id.update_cpassword);
        update_cpassword.setText(user.get(SessionManager.KEY_PASSWORD));
        update_button = findViewById(R.id.update_button);
        
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();
            }
        });
    }
    public void UpdateUser(){
        if (!validate()) {
            return;
        }
    
        final String name = update_fname.getText().toString();
        final String lname = update_lname.getText().toString();
        final String id = update_id.getText().toString();
        final String phone = update_phone.getText().toString();
        final String location = update_location.getText().toString();
        final String password = update_password.getText().toString();
        final String email = user.get(SessionManager.KEY_EMAIL);
    
        final ProgressDialog progressDialog = new ProgressDialog(Update.this);
        progressDialog.setMessage("Updating...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                        
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                        
                            if (success.equals("1")){
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Update.this);
                                builder1.setMessage("Upload success, Please login again...");
                                builder1.setCancelable(false);
    
                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                session.logoutUser();
                                                finish();
                                            }
                                        });
    
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }else {
                                // Toast.makeText(InstallationActivity.this, "", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Update.this);
                                builder1.setMessage("Please try again.");
                                builder1.setCancelable(false);
    
                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                onBackPressed();
                                            }
                                        });
    
    
    
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Update.this, "Try again", Toast.LENGTH_LONG).show();
                        }
                    
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Update.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> map = new HashMap<>();
                map.put("fname", name);
                map.put("lname", lname);
                map.put("id", id);
                map.put("phone", phone);
                map.put("location", location);
                map.put("password", password);
                map.put("email", email);
                return map;
            }
        };
    
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    
    }
    
    public boolean validate() {
        boolean valid = true;
        
        String name = update_fname.getText().toString();
        String lname = update_lname.getText().toString();
        String id = update_id.getText().toString();
        String phone = update_phone.getText().toString();
        String location = update_location.getText().toString();
        String password = update_password.getText().toString();
        String vpassword = update_cpassword.getText().toString();
        
        if (name.isEmpty() || name.length() < 3) {
            update_fname.setError("at least 3 characters");
            valid = false;
        } else {
            update_fname.setError(null);
        }
        
        
        if (lname.isEmpty() || lname.length() < 3) {
            update_lname.setError("at least 3 characters");
            valid = false;
        } else {
            update_lname.setError(null);
        }
        
        if (id.isEmpty()) {
            update_id.setError("ID No is required");
            valid = false;
        } else {
            update_id.setError(null);
        }
        
        if (phone.isEmpty()) {
            update_phone.setError("Phone No is required");
            valid = false;
        } else {
            update_phone.setError(null);
        }
        
        if (location.isEmpty()) {
            update_location.setError("Location is required");
            valid = false;
        } else {
            update_location.setError(null);
        }
        
        if (password.isEmpty() || password.length() < 2 || password.length() > 10) {
            update_password.setError("between 2 and 10 alphanumeric characters");
            valid = false;
        } else {
            update_password.setError(null);
        }
        
        if (!password.equals(vpassword)) {
            update_cpassword.setError("Passwords dint match");
            valid = false;
        } else {
            update_cpassword.setError(null);
        }
        
        return valid;
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
