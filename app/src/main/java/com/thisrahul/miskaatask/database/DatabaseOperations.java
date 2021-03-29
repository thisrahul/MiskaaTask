package com.thisrahul.miskaatask.database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.thisrahul.miskaatask.model.CountryDetail;

import java.util.List;

public class DatabaseOperations {

    private static CountryDatabase countryDatabase;

    public DatabaseOperations(Context context) {
        if (countryDatabase == null) {
            String DATABASE_NAME = "country-database";
            countryDatabase = Room.databaseBuilder(context.getApplicationContext(), CountryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void insertCountry(List<CountryDetail> countryDetails) {
        countryDatabase.clearAllTables();
        for (CountryDetail countryDetail : countryDetails) {
            countryDatabase.countryDao().insertCountry(new CountryEntity(countryDetail.getName()
                    , countryDetail.getCapital()
                    , countryDetail.getRegion()
                    , countryDetail.getSubRegion()
                    , countryDetail.getPopulation()
                    , countryDetail.getBorders()
                    , countryDetail.getLanguages()
                    , countryDetail.getFlag()));
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteCountries() {
        countryDatabase.countryDao().deleteAllCountries();
    }

    @SuppressLint("StaticFieldLeak")
    public List<CountryEntity> getAllCountries() {
        return countryDatabase.countryDao().getAllCountries();
    }

}
