package com.thisrahul.miskaatask.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thisrahul.miskaatask.adapter.CountryAdapter;


import com.thisrahul.miskaatask.database.CountryDatabase;
import com.thisrahul.miskaatask.database.CountryEntity;
import com.thisrahul.miskaatask.databinding.ActivityMainBinding;
import com.thisrahul.miskaatask.model.CountryDetail;
import com.thisrahul.miskaatask.network.ApiInterface;
import com.thisrahul.miskaatask.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<CountryDetail> countryDetailList;

    private  RecyclerView rvCountry;

    private ProgressDialog progressDialog;

    private CountryDatabase countryDatabase;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvCountry = binding.rvCountries;
        rvCountry.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCountry.setLayoutManager(layoutManager);


        if (Util.hasInternetConnection(this)){
            progressDialog = Util.getProgressDialog(this);
            getDataOnline();
            progressDialog.show();
        }else {
            getDataOffline();

            Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataOffline() {
        countryDatabase = CountryDatabase.getInstance(MainActivity.this);
        List<CountryEntity> countryEntityList = countryDatabase.countryDao().getAllCountries();
        if (countryEntityList.size() == 0){
            binding.txtNoFound.setVisibility(View.VISIBLE);
            binding.rvCountries.setVisibility(View.GONE);
        }
        CountryAdapter adapter = new CountryAdapter(countryEntityList,MainActivity.this,false);
        rvCountry.setAdapter(adapter);

        binding.btnDelete.setVisibility(View.VISIBLE);
        binding.btnDelete.setOnClickListener(v ->{
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Warning!!")
                    .setMessage("Are you sure you want to delete")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        countryDatabase.countryDao().deleteAllCountries();
                        binding.txtNoFound.setVisibility(View.VISIBLE);
                        binding.rvCountries.setVisibility(View.GONE);
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });
    }

    private void getDataOnline() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/region/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getCountries().enqueue(new Callback<List<CountryDetail>>() {
            @Override
            public void onResponse(Call<List<CountryDetail>> call, Response<List<CountryDetail>> response) {
              if (progressDialog.isShowing()) progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_SHORT).show();
                } else {
                    countryDetailList = response.body();
                    if (countryDetailList.size() == 0){
                        binding.txtNoFound.setVisibility(View.VISIBLE);
                        binding.rvCountries.setVisibility(View.GONE);
                    }
                    saveOfflineData(countryDetailList);
                    CountryAdapter adapter = new CountryAdapter(MainActivity.this,countryDetailList,true);
                    rvCountry.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<CountryDetail>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOfflineData(List<CountryDetail> countryDetailList) {
        countryDatabase = CountryDatabase.getInstance(MainActivity.this);

        countryDatabase.clearAllTables();
        for (CountryDetail countryDetail : countryDetailList){
            String path = downloadImage(countryDetail.getFlag(),countryDetail.getName());
            countryDatabase.countryDao().insertCountry(new CountryEntity(countryDetail.getName()
                    ,countryDetail.getCapital()
                    ,countryDetail.getRegion()
                    ,countryDetail.getSubRegion()
                    ,countryDetail.getPopulation()
                    ,countryDetail.getBorders()
                    ,countryDetail.getLanguages()
                    ,path));
//            DownloadImageTask downloadImageTask = new DownloadImageTask(countryDetail,countryDatabase);
//            downloadImageTask.execute();
        }
    }

    private String downloadImage(String url,String name){
        FileOutputStream fos = null;
        File localFile = null;
        try {
           localFile = File.createTempFile(name, "jpg");
            fos = new FileOutputStream(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream finalFos = fos;
        Picasso.get()
                .load(url)
                .into(new Target() {
                          @Override
                          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                              try {
                                  // Use the compress method on the BitMap object to write image to the OutputStream
                                  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, finalFos);
                              } catch (Exception e) {
                                  e.printStackTrace();
                              } finally {
                                  try {
                                      finalFos.close();
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }
                              }
                          }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                      }
                );

        return localFile.getAbsolutePath();
    }
}