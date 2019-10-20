package com.dwakenya.homecarehub.newdwa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeFeedback extends AppCompatActivity {
    
    Spinner workload, salary, conditions, communication;
    EditText employee_name, employee_location, employee_comments;
    Button employee_submit;
    TextView employee_request, employee_perform;
    
    String EMPLOYEE_URL = "https://techsavanna.net:8181/dwa/EmployeeFeedback.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_feedback);
    
        employee_name = findViewById(R.id.employee_name);
        employee_request = findViewById(R.id.employee_request);
        employee_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            
            }
        });
        employee_location = findViewById(R.id.employee_location);
        employee_perform = findViewById(R.id.employee_perform);
        employee_perform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker();
            
            }
        });
        employee_comments = findViewById(R.id.employee_comments);
        employee_submit = findViewById(R.id.employee_submit);
        employee_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    
                submitEmployeeFeed();
        
            }
        });
    
        workload = findViewById(R.id.sp_workload);
        List<String> workloadlist = new ArrayList<>();
        workloadlist.add("Excellent");
        workloadlist.add("Good");
        workloadlist.add("Satisfactory");
        workloadlist.add("Unsatisfactory");
    
        ArrayAdapter<String> workloadadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, workloadlist);
        workloadadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workload.setAdapter(workloadadapter);
        workload.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String workloaditemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        salary = findViewById(R.id.sp_salary);
        List<String> salarylist = new ArrayList<>();
        salarylist.add("Excellent");
        salarylist.add("Good");
        salarylist.add("Satisfactory");
        salarylist.add("Unsatisfactory");
    
        ArrayAdapter<String> salaryadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, salarylist);
        salaryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salary.setAdapter(salaryadapter);
        salary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String salaryitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        conditions = findViewById(R.id.sp_conditions);
        List<String> conditionslist = new ArrayList<>();
        conditionslist.add("Excellent");
        conditionslist.add("Good");
        conditionslist.add("Satisfactory");
        conditionslist.add("Unsatisfactory");
    
        ArrayAdapter<String> conditionsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conditionslist);
        conditionsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditions.setAdapter(conditionsadapter);
        conditions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String conditionsitemvalue = parent.getItemAtPosition(position).toString();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            
            }
        });
    
        communication = findViewById(R.id.sp_communication);
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
    }
    
    private void DateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
    
        DatePickerDialog.OnDateSetListener dateSetListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    
                        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                        employee_perform.setText(currentDateString);
                    
                    }
                };
        DatePickerDialog datePickerDialog = new
                DatePickerDialog(this, dateSetListener,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    
    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
    
        DatePickerDialog.OnDateSetListener dateSetListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    
                        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                        employee_request.setText(currentDateString);
                    
                    }
                };
        DatePickerDialog datePickerDialog = new
                DatePickerDialog(this, dateSetListener,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    
    private boolean validate() {
        boolean valid = true;
    
        String _name = employee_name.getText().toString().trim();
        String _request = employee_request.getText().toString().trim();
        String _location = employee_location.getText().toString().trim();
        String _perform = employee_perform.getText().toString().trim();
        String _comments = employee_comments.getText().toString().trim();
        
    
        if (_name.isEmpty()) {
            employee_name.setError("Employee name is required");
            valid = false;
        } else {
            employee_name.setError(null);
        }
    
    
        if (_request.isEmpty()) {
            employee_request.setError("Requested date is required");
            valid = false;
        } else {
            employee_request.setError(null);
        }
    
        if (_location.isEmpty()) {
            employee_location.setError("Service location is required");
            valid = false;
        } else {
            employee_location.setError(null);
        }
    
        if (_perform.isEmpty()) {
            employee_perform.setError("Date of service is required");
            valid = false;
        } else {
            employee_perform.setError(null);
        }
    
        if (_comments.isEmpty()) {
            employee_comments.setError("Comment is required");
            valid = false;
        } else {
            employee_comments.setError(null);
        }
    
        
    
        return valid;
    }
    
    private void submitEmployeeFeed() {
        if (!validate()) {
            return;
        }
    
        final String Sworkload = workload.getSelectedItem().toString();
        final String Ssalary = salary.getSelectedItem().toString();
        final String Scommunication = communication.getSelectedItem().toString();
        final String Sconditions = conditions.getSelectedItem().toString();
        final String Semployee_perform = employee_perform.getText().toString().trim();
        final String Semployee_comments = employee_comments.getText().toString().trim();
        final String Semployee_name = employee_name.getText().toString().trim();
        final String Semployee_location = employee_location.getText().toString().trim();
        final String Semployee_request = employee_request.getText().toString().trim();
        
        final ProgressDialog progressDialog = new ProgressDialog(EmployeeFeedback.this);
        progressDialog.setMessage("Submitting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EMPLOYEE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
//                        Log.e(TAG, "respon"+response);
                        try {
                        
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                        
                            if (success.equals("1")){
                                Toast.makeText(EmployeeFeedback.this, "Submitted successfully", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(EmployeeFeedback.this, "Try again", Toast.LENGTH_LONG).show();
                        }
                    
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EmployeeFeedback.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> map = new HashMap<>();
                map.put("workload", Sworkload);
                map.put("employee_perform", Semployee_perform);
                map.put("salary", Ssalary);
                map.put("conditions", Sconditions);
                map.put("communication", Scommunication);
                map.put("employee_comments", Semployee_comments);
                map.put("employee_name", Semployee_name);
                map.put("employee_request", Semployee_request);
                map.put("employee_location", Semployee_location);
                return map;
            }
        };
    
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    
    
    }
    
    public void onBackPressed(){
        super.onBackPressed();
    }
}
