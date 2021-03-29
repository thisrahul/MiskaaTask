package com.thisrahul.miskaatask.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CountryEntity.class}, version = 7, exportSchema = false)
public abstract class CountryDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
}
