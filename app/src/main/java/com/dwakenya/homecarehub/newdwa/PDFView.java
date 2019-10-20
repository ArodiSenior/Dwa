package com.dwakenya.homecarehub.newdwa;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import pub.devrel.easypermissions.EasyPermissions;

public class PDFView extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    FabSpeedDial fabSpeedDial;
    private static final int WRITE_REQUEST_CODE = 300;
    private static final String TAG = PDFView.class.getSimpleName();
    String ImagePath;
    com.github.barteksc.pdfviewer.PDFView pdfView;
    ProgressBar ProgressPdf;
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
    
        isConnected();
    
        ProgressPdf = findViewById(R.id.ProgressPdf);
        ProgressPdf.setVisibility(View.VISIBLE);
        pdfView = findViewById(R.id.PdfView);
    
        fabSpeedDial = findViewById(R.id.PDFDial);
        final Intent intent = getIntent();
        ImagePath = intent.getStringExtra("PDFUrl");
    
        new RetrievePdfStream().execute(ImagePath);
        
        final String fileName = ImagePath.substring(ImagePath.lastIndexOf('/')+1);
    
    
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }
        
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_download:
                        Download();
                        break;
                    case R.id.action_share:
                        File outputFile = new File(Environment.getExternalStorageDirectory() + File.separator + "DWA Resources/", fileName);
                        if (outputFile.exists()){
                            Uri uri = Uri.fromFile(outputFile);
                            Intent intent1 = new Intent(Intent.ACTION_SEND);
                            intent1.setType("application/pdf");
                            intent1.putExtra(Intent.EXTRA_STREAM, uri );
                            startActivity(Intent.createChooser(intent1, "Share using"));
                        }
                        else {
                            Toast.makeText(PDFView.this, "Download the file first", Toast.LENGTH_SHORT).show();
                        }
                        break;
    
                }
                return true;
            }
        });
    
    }
    
    public void Download(){
        if (CheckSD.isSDCardPresent()) {
        
            //check if app has permission to write to the external storage.
        
            if (EasyPermissions.hasPermissions(PDFView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Get the URL entered
                new DownloadFile(PDFView.this).execute(ImagePath);
            
            } else {
                //If permission is not present request for the same.
                EasyPermissions.requestPermissions(PDFView.this, getString(R.string.write_file), WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        
        
        } else {
            Toast.makeText(getApplicationContext(),
                    "SD Card not found", Toast.LENGTH_LONG).show();
        
        }
    }
    
    public  boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            final AlertDialog.Builder builder = new AlertDialog.Builder(PDFView.this);
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
            return false;
        }
        return true;
    }
    
    public class RetrievePdfStream extends AsyncTask<String, Void, InputStream>{
    
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                 HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e){
                return null;
            }
            return inputStream;
        }
    
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
    
        @Override
        protected void onPostExecute(InputStream inputStream) {
            ProgressPdf.setVisibility(View.GONE);
            fabSpeedDial.setVisibility(View.VISIBLE);
            pdfView.fromStream(inputStream)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .enableAnnotationRendering(true)
                    .spacing(10)
                    .load();
        }
        
    }
    
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, PDFView.this);
        }
    
      
        public void onPermissionsGranted(int requestCode, List<String> perms) {
            //Download the file once permission is granted
            new DownloadFile(PDFView.this).execute(ImagePath);
        }
    
        @Override
        public void onPermissionsDenied(int requestCode, List<String> perms) {
            Log.d(TAG, "Permission has been denied");
        }
        
    
        
    
    
}
