package com.thisrahul.miskaatask.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thisrahul.miskaatask.adapter.CountryAdapter;
import com.thisrahul.miskaatask.database.CountryEntity;
import com.thisrahul.miskaatask.database.DatabaseOperations;
import com.thisrahul.miskaatask.databinding.ActivityMainBinding;
import com.thisrahul.miskaatask.model.CountryDetail;
import com.thisrahul.miskaatask.network.ApiInterface;
import com.thisrahul.miskaatask.network.NetworkService;
import com.thisrahul.miskaatask.utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<CountryDetail> countryDetailList;

    private RecyclerView rvCountry;

    private ProgressDialog progressDialog;

    private DatabaseOperations databaseOperations;

    private ApiInterface apiInterface;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseOperations = new DatabaseOperations(this);

        rvCountry = binding.rvCountries;
        rvCountry.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCountry.setLayoutManager(layoutManager);


        if (Util.hasInternetConnection(this)) {
            new NetworkService(getCacheDir());
            progressDialog = Util.getProgressDialog(this);
            progressDialog.show();
            getDataOnline();
        } else {
            getDataOffline();
            Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataOffline() {
        List<CountryEntity> countryEntityList = databaseOperations.getAllCountries();
        if (countryEntityList.size() == 0) {
            binding.txtNoFound.setVisibility(View.VISIBLE);
            binding.rvCountries.setVisibility(View.GONE);
        }
        CountryAdapter adapter = new CountryAdapter(countryEntityList, MainActivity.this, false);
        rvCountry.setAdapter(adapter);

        binding.btnDelete.setVisibility(View.VISIBLE);
        binding.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Warning!!")
                    .setMessage("Are you sure you want to delete")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        databaseOperations.deleteCountries();
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
        apiInterface = NetworkService.getInstance().getWebApi();

        apiInterface.getCountries().enqueue(new Callback<List<CountryDetail>>() {
            @Override
            public void onResponse(Call<List<CountryDetail>> call, Response<List<CountryDetail>> response) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_SHORT).show();
                } else {
                    countryDetailList = response.body();
                    if (countryDetailList.size() == 0) {
                        binding.txtNoFound.setVisibility(View.VISIBLE);
                        binding.rvCountries.setVisibility(View.GONE);
                    }
                    saveOfflineData(countryDetailList);
                    CountryAdapter adapter = new CountryAdapter(MainActivity.this, countryDetailList, true);
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
        databaseOperations.insertCountry(countryDetailList);
    }
}