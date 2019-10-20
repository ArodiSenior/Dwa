package com.dwakenya.homecarehub.newdwa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dwakenya.homecarehub.newdwa.app.AppConfig;
import com.dwakenya.homecarehub.newdwa.app.AppController;
import com.dwakenya.homecarehub.newdwa.helpers.CircleTransform;
import com.dwakenya.homecarehub.newdwa.helpers.SessionManager;
import com.dwakenya.homecarehub.newdwa.holders.SpecialityHolder;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeHomeActivity extends AppCompatActivity implements View.OnClickListener {

    // ALWAYS TRYING TO UPDATE FCM ID JUST IN CASE ANOTHER ONE IS PROVIDED
    //ID IS ATTACHED TO LOGGED USERID

    String regId="";

    private String TAG = EmployeeHomeActivity.class.getSimpleName();

    private Spinner spinnerSpeciality, spinnerPreferenceSkill;
    private EditText input_where_learnt, input_training_level;
    private Button btn_add_speciality;
    private FloatingActionButton fabSubmitPreference;
    private ToggleButton tbStatus;
    private ImageView ivProfile;
    private TextView tvPreference;
    private TextView tvName;
    private TextView tvCoordinates;
    private TextView tv_loc;
//    LocationManager locationManager;
    private String getEmail;
    private String getId;
    
    String IMAGE_URL = "https://techsavanna.net:8181/dwa/Upload.php";
    
    String imageUrl = "https://techsavanna.net:8181/dwa/";
    
    
    Bitmap bitmap;
    
    //private Uri imageUri = null;
    //private StorageTask uploadTask;

    ArrayList<SpecialityHolder> specialityHolderArrayList;
    ArrayList<SpecialityHolder> prefenceSkillHolderArrayList;

    String selected_speciality,selected_speciality_id, selected_preference,selected_preference_id;



    // Session Manager Class
    SessionManager session;

    HashMap<String, String> user;
    String status;
    String newStatus;

    private String selected_reg = "",  selected_region_id = "", lat = "", lon="", loc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");

        // Session Manager
        session = new SessionManager(getApplicationContext());

        // get user data from session
        user = session.getUserDetails();
    
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
//        }

//        getLocation();

        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        regId= pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {

            updateUserToken ();


        }else {

           // Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_LONG).show();
        }



        spinnerSpeciality = (Spinner) findViewById(R.id.sp_speciality);
        spinnerPreferenceSkill = (Spinner) findViewById(R.id.sp_preference_skill);
        input_where_learnt = (EditText) findViewById(R.id.input_where_learnt);
        input_training_level = (EditText) findViewById(R.id.input_level_of_training);
        btn_add_speciality = (Button) findViewById(R.id.addSpeciality);
        btn_add_speciality.setOnClickListener(this);
        fabSubmitPreference = (FloatingActionButton) findViewById(R.id.fab_update_preference);
        fabSubmitPreference.setOnClickListener(this);
        tbStatus = findViewById(R.id.tb_status);
        tvPreference = findViewById(R.id.user_profile_short_bio);
        tv_loc = findViewById(R.id.tv_loc);
        TextView Email = findViewById(R.id.Email);
        Email.setText(user.get(SessionManager.KEY_EMAIL));
        TextView Phone = findViewById(R.id.Phone);
        Phone.setText(user.get(SessionManager.KEY_PHONE));

        tvPreference.setText(user.get(SessionManager.KEY_PREFERENCE));

        tvName = findViewById(R.id.user_profile_name);

        tvName.setText(user.get(SessionManager.KEY_NAME));
        getEmail = user.get(SessionManager.KEY_EMAIL);
    
        tv_loc.setText(user.get(SessionManager.KEY_LOCATION));
        
        getId = user.get(SessionManager.KEY_USER_ID);

        ivProfile = findViewById(R.id.user_profile_photo);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(4,3)
                        .start(EmployeeHomeActivity.this);

            }
        });
        tvCoordinates = findViewById(R.id.tv_coordinates);

        if ((!user.get(SessionManager.KEY_IMAGE_PATH).equals("")) || user.get(SessionManager.KEY_IMAGE_PATH) != null) {
            Glide.with(EmployeeHomeActivity.this).load(user.get(SessionManager.KEY_IMAGE_PATH))
                    .crossFade()
                   // .thumbnail(0.5f)
                     .bitmapTransform(new CircleTransform(EmployeeHomeActivity.this))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(ivProfile);
       }

        tbStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeHomeActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Status Change");
                builder.setMessage("Current Status is " + status + "\n" + "Proceed to change status?");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(tbStatus.isChecked())
                        {
                            updateStatus("0");
                        } else {
                            updateStatus("1");
                        }

                    }
                });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (user.get(SessionManager.KEY_STATUS).equals("1")) {
                                    status = "Occupied";
                                    tbStatus.setChecked(false);
                                } else if (user.get(SessionManager.KEY_STATUS).equals("0")) {
                                    status = "Available";
                                    tbStatus.setChecked(true);

                                }
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });

        tbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

//                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeHomeActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
//                builder.setTitle("Status Change");
//                builder.setMessage("Current Status is " + status + "\n" + "Proceed to change status?");
//
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                        if(isChecked)
//                        {
//                            updateStatus("0");
//                        } else {
//                            updateStatus("1");
//                        }
//
//
//                    }
//                });
//
//                builder.setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                if (user.get(SessionManager.KEY_STATUS).equals("1")) {
//                                    status = "Occupied";
//                                    tbStatus.setChecked(false);
//                                } else if (user.get(SessionManager.KEY_STATUS).equals("0")) {
//                                    status = "Available";
//                                    tbStatus.setChecked(true);
//
//                                }
//                                dialog.dismiss();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();


            }
        });
        
        

        specialityHolderArrayList = new ArrayList<>();
        prefenceSkillHolderArrayList = new ArrayList<>();
    
        
        fillSpeciality();


        spinnerSpeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_speciality_id = specialityHolderArrayList.get(position).getId();
                selected_speciality = specialityHolderArrayList.get(position).getSpeciality_name();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerPreferenceSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_preference_id = prefenceSkillHolderArrayList.get(position).getId();
                selected_preference = prefenceSkillHolderArrayList.get(position).getSpeciality_name();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        }
        
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri uri = result.getUri();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    ivProfile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                uploadMultipart(getEmail, getStringImage(bitmap), imageUrl, getId);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }
    
    public void uploadMultipart(final String id, final String photo, final String imageurl, final String getid) {
        final ProgressDialog progressDialog = new ProgressDialog(EmployeeHomeActivity.this);
        progressDialog.setMessage("Updating...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    
        StringRequest stringRequest = new StringRequest(Request.Method.POST, IMAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e(TAG, "respon"+response);
                        try {
                            
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            
                            if (success.equals("1")){
                                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(EmployeeHomeActivity.this);
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
    
                                android.app.AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }else {
                                // Toast.makeText(InstallationActivity.this, "", Toast.LENGTH_SHORT).show();
                                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(EmployeeHomeActivity.this);
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
    
    
    
                                android.app.AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(EmployeeHomeActivity.this, "Try again", Toast.LENGTH_LONG).show();
                        }
    
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EmployeeHomeActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> map = new HashMap<>();
                map.put("email", getEmail);
                map.put("photo", getStringImage(bitmap));
                map.put("url", imageUrl);
                map.put("id", getId);
                return map;
            }
        };
    
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    
    
    
    }
    
    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
        
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (user.get(SessionManager.KEY_STATUS).equals("1")) {
            status = "Occupied";
            tbStatus.setChecked(false);
        } else if (user.get(SessionManager.KEY_STATUS).equals("0")) {
            status = "Available";
            tbStatus.setChecked(true);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {


            case R.id.action_logout:

                session.logoutUser();
                finish();

                break;

            case R.id.action_feedback:

                Intent i2= new Intent(this, FeedBackActivity.class);
                startActivity(i2);

                break;

            case R.id.action_refresh:

                refresh();

                break;
            case R.id.action_update:
                Intent intent = new Intent(EmployeeHomeActivity.this, Update.class);
                startActivity(intent);
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

        return true;
    }
    
//    void getLocation() {
//        try {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
//        }
//        catch(SecurityException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void onLocationChanged(Location location) {
//        try {
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            tvCoordinates.setText(addresses.get(0).getLocality());
//            tv_loc.setText(addresses.get(0).getAddressLine(0));
//
//             }catch(Exception e)
//        {
//
//        }
//
//    }

//    @Override
//    public void onProviderDisabled(String provider) {
//        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }

    public void refresh (){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void fillSpeciality() {
        Thread thread = new Thread() {
            Handler handler = new Handler();
            ProgressDialog progressDialog;
            boolean error;
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(EmployeeHomeActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                    }
                });



                final List<String> list = new ArrayList<String>();
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                specialityHolderArrayList.clear();
                adapter.notifyDataSetChanged();


                final List<String> list2 = new ArrayList<String>();
                final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list2);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                prefenceSkillHolderArrayList.clear();
                adapter2.notifyDataSetChanged();


                HashMap<String, String> params = new HashMap<String, String>();
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                        AppConfig.SERVER_URL + "fill_speciality.php", new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                progressDialog.dismiss();
                                Log.d(TAG, " On Response: " + response.toString());
                                try {
                                    // Json parsing from response
                                    JSONObject object = new JSONObject(response.toString());
                                    // check error
                                    error = object.getBoolean("error");
                                    if (!error) {
                                        // iterate over array users as declared in php script
                                        JSONArray jArray = object.getJSONArray("specialities");
                                        Log.d(TAG, "Array: " + jArray.toString());

                                        for (int i = 0; i < jArray.length(); i++) {
                                            JSONObject singleRow = jArray.getJSONObject(i);
                                            SpecialityHolder specialityHolder = new SpecialityHolder(
                                                    singleRow.getString("id"), singleRow.getString("speciality_name"));
                                            specialityHolderArrayList.add(specialityHolder);
                                            prefenceSkillHolderArrayList.add(specialityHolder);
                                        }
                                        for (int i = 0; i < specialityHolderArrayList.size(); i++) {
                                            list.add(specialityHolderArrayList.get(i).getSpeciality_name());
                                            // list.add(locationList.get(i).getId());
                                        }


                                        spinnerSpeciality.setAdapter(adapter);

                                        for (int i = 0; i < prefenceSkillHolderArrayList.size(); i++) {
                                            list2.add(prefenceSkillHolderArrayList.get(i).getSpeciality_name());
                                            // list.add(locationList.get(i).getId());
                                        }
                                        spinnerPreferenceSkill.setAdapter(adapter2);

                                    } else {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "Equipment available", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }

                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        NetworkResponse errorRes = error.networkResponse;
                        String stringData = "";
                        if (errorRes != null && errorRes.data != null) {
                            try {
                                stringData = new String(errorRes.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("Error", stringData);

                        Log.e(TAG, "On ErrorResponse: " + error.getMessage());
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");

                        return headers;
                    }

                };

                // add the request object to the queue to be executed
                AppController.getInstance().addToRequestQueue(req);
            }

        };
        thread.start();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_add_speciality)) {
            if (input_where_learnt.getText().equals("")) {

                Toast.makeText(getApplicationContext(), "Where learnt?", Toast.LENGTH_LONG).show();
            } else {
                addSpeciality();


            }
        }

        if (v.equals(fabSubmitPreference)) {
            updatePreference();
        }
    }

    private void updatePreference () {
        Thread thread = new Thread() {
            ProgressDialog progressDialog;
            Handler handler = new Handler();
            boolean error;

            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(EmployeeHomeActivity.this,
                                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Submitting Preference...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                    }
                });


                HashMap<String, String> params = new HashMap<String, String>();

                params.put("user_id", user.get(SessionManager.KEY_USER_ID));
                params.put("speciality_id", selected_preference_id);
                params.put("preference_skill", selected_preference);


                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                        AppConfig.SERVER_URL + "update_preference.php", new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                progressDialog.dismiss();
                                Log.d(TAG, " On Response: " + response.toString());
                                try {

                                    // Json parsing from response
                                    JSONObject object = new JSONObject(response.toString());

                                    // check error
                                    error = object.getBoolean("error");
                                    if (!error) {
                                        Toast.makeText(getBaseContext(), "Preference added successfully", Toast.LENGTH_LONG).show();

                                        input_where_learnt.setText("");


                                    } else {
                                        Toast.makeText(getBaseContext(), "Failed : " + object.getString("error_msg"), Toast.LENGTH_LONG).show();
                                    }
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        NetworkResponse errorRes = error.networkResponse;
                        String stringData = "";
                        if (errorRes != null && errorRes.data != null) {
                            try {
                                stringData = new String(errorRes.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("Error", stringData);

                        Log.e(TAG, "On ErrorResponse: " + error.getMessage());
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");

                        return headers;
                    }

                };

                // add the request object to the queue to be executed
                AppController.getInstance().addToRequestQueue(req);
            }

        };
        thread.start();
    }

    private void updateStatus (final String newStatus) {
        Thread thread = new Thread() {
            ProgressDialog progressDialog;
            Handler handler = new Handler();
            boolean error;

            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(EmployeeHomeActivity.this,
                                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Updating Status...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                    }
                });


                HashMap<String, String> params = new HashMap<String, String>();

                params.put("user_id", user.get(SessionManager.KEY_USER_ID));
                params.put("status", newStatus);


                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                        AppConfig.SERVER_URL + "update_status.php", new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                progressDialog.dismiss();
                                Log.d(TAG, " On Response: " + response.toString());
                                try {

                                    // Json parsing from response
                                    JSONObject object = new JSONObject(response.toString());

                                    // check error
                                    error = object.getBoolean("error");
                                    if (!error) {
                                        Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG).show();
                                        refresh();


                                    } else {
                                        Toast.makeText(getBaseContext(), "Failed : " + object.getString("error_msg"), Toast.LENGTH_LONG).show();
                                    }
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        NetworkResponse errorRes = error.networkResponse;
                        String stringData = "";
                        if (errorRes != null && errorRes.data != null) {
                            try {
                                stringData = new String(errorRes.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("Error", stringData);

                        Log.e(TAG, "On ErrorResponse: " + error.getMessage());
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");

                        return headers;
                    }

                };

                // add the request object to the queue to be executed
                AppController.getInstance().addToRequestQueue(req);
            }

        };
        thread.start();
    }

    private void addSpeciality () {
        Thread thread = new Thread() {
            ProgressDialog progressDialog;
            Handler handler = new Handler();
            boolean error;

            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(EmployeeHomeActivity.this,
                                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Submitting...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                    }
                });


                HashMap<String, String> params = new HashMap<String, String>();

                params.put("user_id", user.get(SessionManager.KEY_USER_ID));
                params.put("email1", user.get(SessionManager.KEY_EMAIL));
                params.put("skill", selected_speciality);
                params.put("speciality_id", selected_speciality_id);
                params.put("where_learnt", input_where_learnt.getText().toString());
                params.put("training_level", input_training_level.getText().toString());

                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                        AppConfig.SERVER_URL + "add_employee_speciality.php", new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                progressDialog.dismiss();
                                Log.d(TAG, " On Response: " + response.toString());
                                try {

                                    // Json parsing from response
                                    JSONObject object = new JSONObject(response.toString());

                                    // check error
                                    error = object.getBoolean("error");
                                    if (!error) {
                                        Toast.makeText(getBaseContext(), "Speciality added successfully", Toast.LENGTH_LONG).show();

                                        input_where_learnt.setText("");


                                    } else {
                                        Toast.makeText(getBaseContext(), "Failed : " + object.getString("error_msg"), Toast.LENGTH_LONG).show();
                                    }
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        NetworkResponse errorRes = error.networkResponse;
                        String stringData = "";
                        if (errorRes != null && errorRes.data != null) {
                            try {
                                stringData = new String(errorRes.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("Error", stringData);

                        Log.e(TAG, "On ErrorResponse: " + error.getMessage());
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");

                        return headers;
                    }

                };

                // add the request object to the queue to be executed
                AppController.getInstance().addToRequestQueue(req);
            }

        };
        thread.start();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        regId= pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {

           // Toast.makeText(getApplicationContext(), "Firebase Reg Id: " + regId, Toast.LENGTH_LONG).show();
        }else {

           // Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_LONG).show();
        }
    }

    private void updateUserToken () {



        Thread thread = new Thread() {

            boolean error;

            @Override
            public void run() {


                HashMap<String, String> params = new HashMap<String, String>();


                params.put("user_id", user.get(SessionManager.KEY_USER_ID));
                params.put("fcm_token", regId);


                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                        AppConfig.SERVER_URL + "fcm_registration.php", new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                Log.d(TAG, " On Response: " + response.toString());
                                try {

                                    // Json parsing from response
                                    JSONObject object = new JSONObject(response.toString());

                                    // check error
                                    error = object.getBoolean("error");
                                    if (!error) {

                                    } else {

                                    }
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse errorRes = error.networkResponse;
                        String stringData = "";
                        if (errorRes != null && errorRes.data != null) {
                            try {
                                stringData = new String(errorRes.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("Error", stringData);

                        Log.e(TAG, "On ErrorResponse: " + error.getMessage());
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");

                        return headers;
                    }

                };

                // add the request object to the queue to be executed
                AppController.getInstance().addToRequestQueue(req);
            }

        };
        thread.start();
    }
}
