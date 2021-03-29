package com.thisrahul.miskaatask.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.thisrahul.miskaatask.model.Languages;
import com.thisrahul.miskaatask.utils.Converter;

import java.util.List;

@Entity(tableName = "CountryDetail")
public class CountryEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "capital")
    private final String capital;

    @ColumnInfo(name = "region")
    private final String region;

    @ColumnInfo(name = "sub_region")
    private final String subRegion;

    @ColumnInfo(name = "population")
    private final String population;

    @ColumnInfo(name = "borders")
    @TypeConverters({Converter.class})
    private final List<String> borders;

    @ColumnInfo(name = "languages")
    @TypeConverters({Converter.class})
    private final List<Languages> languages;

    @ColumnInfo(name = "flags")
    private final String flags;


    public CountryEntity(String name, String capital, String region, String subRegion, String population, List<String> borders, List<Languages> languages, String flags) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subRegion = subRegion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
        this.flags = flags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public String getPopulation() {
        return population;
    }

    public List<String> getBorders() {
        return borders;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public String getFlags() {
        return flags;
    }
}