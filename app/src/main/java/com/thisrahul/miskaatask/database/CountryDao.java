package com.thisrahul.miskaatask.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CountryDao {

    @Insert(onConflict = REPLACE)
    void insertCountry(CountryEntity countryEntity);

    @Query("SELECT * FROM CountryDetail")
    List<CountryEntity> getAllCountries();

    @Query("DELETE  FROM CountryDetail")
    void deleteAllCountries();

}
