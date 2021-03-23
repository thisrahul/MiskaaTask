package com.thisrahul.miskaatask.network;

import com.thisrahul.miskaatask.model.CountryDetail;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("asia")
    Call<List<CountryDetail>> getCountries();
}
