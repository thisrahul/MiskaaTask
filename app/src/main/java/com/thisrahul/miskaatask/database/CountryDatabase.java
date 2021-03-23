package com.thisrahul.miskaatask.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.thisrahul.miskaatask.converter.Converter;

@Database(entities = {CountryEntity.class},version = 5,exportSchema = false)
public abstract class CountryDatabase extends RoomDatabase {

    private static CountryDatabase countryDatabase;

    public synchronized static CountryDatabase getInstance(Context context){
        if (countryDatabase == null){
            String DATABASE_NAME = "country-database";
            countryDatabase = Room.databaseBuilder(context.getApplicationContext(),CountryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return countryDatabase;
    }

    public abstract CountryDao countryDao();

}
