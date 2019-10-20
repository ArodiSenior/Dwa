package com.dwakenya.homecarehub.newdwa;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployerFeedback extends AppCompatActivity {
    
    Spinner quality;
    Spinner consistency;
    Spinner motivation;
    Spinner knowledge;
    Spinner communication;
    Spinner judgement;
    Spinner honesty;
    Spinner appearance;
    EditText feed_name;
    EditText feed_comments;
    EditText feed_review;
    EditText feed_period;
    EditText feed_email;
    EditText feed_occupation;
    EditText feed_excellence;
    EditText feed_phone;
    EditText feed_improvement;
    Button feed_submit;
    
    String FEED_URL = "https://techsavanna.net:8181/dwa/EmployerFeedback.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_feedback);
    
        feed_name = findViewById(R.id.feed_name);
        feed_phone = findViewById(R.id.feed_phone);
        feed_email = findViewById(R.id.feed_email);
        feed_occupation = findViewById(R.id.feed_occupation);
        feed_excellence = findViewById(R.id.feed_excellence);
        feed_improvement = findViewById(R.id.feed_improvement);
        feed_review = findViewById(R.id.feed_review);
        feed_period = findViewById(R.id.feed_period);
        feed_comments = findViewById(R.id.feed_comments);
        feed_submit = findViewById(R.id.feed_submit);
        feed_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
                SubmitFeed();
            
            
            }
        });
    
         quality = findViewById(R.id.sp_quality);
        List<String> qualitylist = new ArrayList<>();
        qualitylist.add("Excellent");
        qualitylist.add("Good");
        qualitylist.add("Satisfactory");
        qualitylist.add("Unsatisfactory");
    
        ArrayAdapter<String> qualityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, qualitylist);
        qualityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quality.setAdapter(qualityadapter);
        quality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String qualityitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
        
        consistency = findViewById(R.id.sp_consistency);
        List<String> consistencylist = new ArrayList<>();
        consistencylist.add("Excellent");
        consistencylist.add("Good");
        consistencylist.add("Satisfactory");
        consistencylist.add("Unsatisfactory");
    
        ArrayAdapter<String> consistencyadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, consistencylist);
        consistencyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        consistency.setAdapter(consistencyadapter);
        consistency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String consistencyitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        motivation = findViewById(R.id.sp_motivation);
        List<String> motivationlist = new ArrayList<>();
        motivationlist.add("Excellent");
        motivationlist.add("Good");
        motivationlist.add("Satisfactory");
        motivationlist.add("Unsatisfactory");
    
        ArrayAdapter<String> motivationadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, motivationlist);
        motivationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motivation.setAdapter(motivationadapter);
        motivation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String motivationitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        knowledge = findViewById(R.id.sp_knowledge);
        List<String> knowledgelist = new ArrayList<>();
        knowledgelist.add("Excellent");
        knowledgelist.add("Good");
        knowledgelist.add("Satisfactory");
        knowledgelist.add("Unsatisfactory");
    
        ArrayAdapter<String> knowledgeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, knowledgelist);
        knowledgeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        knowledge.setAdapter(knowledgeadapter);
        knowledge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String knowledgeitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        communication = findViewById(R.id.sp_Communication);
        List<String> communicationlist = new ArrayList<>();
        communicationlist.add("Excellent");
        communicationlist.add("Good");
        communicationlist.add("Satisfactory");
        communicationlist.add("Unsatisfactory");
    
        ArrayAdapter<String> communicationadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, communicationlist);
        communicationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        communication.setAdapter(communicationadapter);
        communication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String communicationitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        judgement = findViewById(R.id.sp_judgement);
        List<String> judgementlist = new ArrayList<>();
        judgementlist.add("Excellent");
        judgementlist.add("Good");
        judgementlist.add("Satisfactory");
        judgementlist.add("Unsatisfactory");
    
        ArrayAdapter<String> judgementadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, judgementlist);
        judgementadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        judgement.setAdapter(judgementadapter);
        judgement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String judgementitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        honesty = findViewById(R.id.sp_honesty);
        List<String> honestylist = new ArrayList<>();
        honestylist.add("Excellent");
        honestylist.add("Good");
        honestylist.add("Satisfactory");
        honestylist.add("Unsatisfactory");
    
        ArrayAdapter<String> honestyadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, honestylist);
        honestyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        honesty.setAdapter(honestyadapter);
        honesty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String honestyitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        appearance = findViewById(R.id.sp_appearance);
        List<String> appearancelist = new ArrayList<>();
        appearancelist.add("Excellent");
        appearancelist.add("Good");
        appearancelist.add("Satisfactory");
        appearancelist.add("Unsatisfactory");
    
        ArrayAdapter<String> appearanceadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, appearancelist);
        appearanceadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appearance.setAdapter(communicationadapter);
        appearance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String appearanceitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    }
    
    public void SubmitFeed() {
        final String Squality = quality.getSelectedItem().toString();
        final String Sappearance = appearance.getSelectedItem().toString();
        final String Shonesty = honesty.getSelectedItem().toString();
        final String Sjudgement = judgement.getSelectedItem().toString();
        final String Scommunication = communication.getSelectedItem().toString();
        final String Sknowledge = knowledge.getSelectedItem().toString();
        final String Smotivation = motivation.getSelectedItem().toString();
        final String Sconsistency = consistency.getSelectedItem().toString();
    
        final String Sfeed_comments = feed_comments.getText().toString();
        final String Sfeed_name = feed_name.getText().toString();
        final String Sfeed_occupation = feed_occupation.getText().toString();
        final String Sfeed_email = feed_email.getText().toString();
        final String Sfeed_period = feed_period.getText().toString();
        final String Sfeed_review = feed_review.getText().toString();
        final String Sfeed_improvement = feed_improvement.getText().toString();
        final String Sfeed_excellence = feed_excellence.getText().toString();
        final String Sfeed_phone = feed_phone.getText().toString();
        if (!validate()) {
        }else {
            final ProgressDialog progressDialog = new ProgressDialog(EmployerFeedback.this);
            progressDialog.setMessage("Submitting...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
    
            StringRequest stringRequest = new StringRequest(Request.Method.POST, FEED_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
//                        Log.e(TAG, "respon"+response);
                            try {
                        
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                        
                                if (success.equals("1")) {
                                    Toast.makeText(EmployerFeedback.this, "Submitted successfully", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(EmployerFeedback.this, "Try again", Toast.LENGTH_LONG).show();
                            }
                    
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(EmployerFeedback.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("quality", Squality);
                    map.put("consistency", Sconsistency);
                    map.put("motivation", Smotivation);
                    map.put("knowledge", Sknowledge);
                    map.put("communication", Scommunication);
                    map.put("judgement", Sjudgement);
                    map.put("honesty", Shonesty);
                    map.put("appearance", Sappearance);
                    map.put("employee_name", Sfeed_name);
                    map.put("employee_comments", Sfeed_comments);
                    map.put("employee_review", Sfeed_review);
                    map.put("employee_period", Sfeed_period);
                    map.put("employee_phone", Sfeed_phone);
                    map.put("employee_excellence", Sfeed_excellence);
                    map.put("employee_improvement", Sfeed_improvement);
                    map.put("feed_occupation", Sfeed_occupation);
                    map.put("feed_email", Sfeed_email);
                    return map;
                }
            };
    
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    
        
    }
    
    public boolean validate() {
        boolean valid = true;
        
        String _name = feed_name.getText().toString().trim();
        String _phone = feed_phone.getText().toString().trim();
        String _email = feed_email.getText().toString().trim();
        String _occupation = feed_occupation.getText().toString().trim();
        String _excellence = feed_excellence.getText().toString().trim();
        String _improvement = feed_improvement.getText().toString().trim();
        String _review = feed_review.getText().toString().trim();
        String _period = feed_period.getText().toString().trim();
        String _comments = feed_comments.getText().toString().trim();
        if (_name.isEmpty()) {
            feed_name.setError("Employee name is required");
            valid = false;
        }else {
            feed_name.setError(null);
        }
        
        
        if (_comments.isEmpty()) {
            feed_comments.setError("Comment is required");
            valid = false;
        }else {
            feed_comments.setError(null);
        }
        
        if (_review.isEmpty()) {
            feed_review.setError("Achieved goals set in previous review is required");
            valid = false;
        }else {
            feed_review.setError(null);
        }
        
        if (_period.isEmpty()) {
            feed_period.setError("Goals for next review period is required");
            valid = false;
        }else {
            feed_period.setError(null);
        }
        
        if (_phone.isEmpty()) {
            feed_phone.setError("Employee phone is required");
            valid = false;
        }else {
            feed_phone.setError(null);
        }
        if (_email.isEmpty()) {
            feed_email.setError("Employee email is required");
            valid = false;
        }else {
            feed_email.setError(null);
        }
        if (_occupation.isEmpty()) {
            feed_occupation.setError("Employee occupation is required");
            valid = false;
        }else {
            feed_occupation.setError(null);
        }
        if (_excellence.isEmpty()) {
            feed_excellence.setError("Areas of excellence is required");
            valid = false;
        }else {
            feed_excellence.setError(null);
        }
        if (_improvement.isEmpty()) {
            feed_improvement.setError("Areas of improvement is required");
            valid = false;
        }else {
            feed_improvement.setError(null);
        }
        return valid;
    }
    
    public void onBackPressed(){
        super.onBackPressed();
    }
}
