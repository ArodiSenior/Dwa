package com.dwakenya.homecarehub.newdwa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Image extends AppCompatActivity {
    public static  String GET_IMAGE="https://techsavanna.net:8181/dwa/videoApis.php";
    RecyclerView recyclerView;
    List<ImageModel> imageModels = new ArrayList<>();
    ImageAdapter imageAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        recyclerView = findViewById(R.id.ImageRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        
    
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    
    
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_IMAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                            
                                JSONObject object = array.getJSONObject(i);
                                String name = object.getString("name");
                                String image = object.getString("image");
                                String category = object.getString("category");
                            
                                ImageModel imageModel = new ImageModel(image, name);
                                if (category.equals("images")) {
                                    progressDialog.dismiss();
                                    imageModels.add(imageModel);
                                }
                            }
                            imageAdapter = new ImageAdapter(Image.this, imageModels);
                            recyclerView.setAdapter(imageAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Image.this);
                builder.setMessage("No internet connection...");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                    }
                });
            
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            
            }
        
        
        });
    
    
    
        Volley.newRequestQueue(Image.this).add(stringRequest);
    }
    
    
}
