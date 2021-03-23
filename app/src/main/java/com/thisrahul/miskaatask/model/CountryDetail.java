package com.thisrahul.miskaatask.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryDetail {

    @SerializedName("name")
    private String name;

    @SerializedName("capital")
    private String capital;

    @SerializedName("region")
    private String region;

    @SerializedName("subregion")
    private String subRegion;

    @SerializedName("population")
    private String population;

    @SerializedName("borders")
    private List<String> borders;

    @SerializedName("languages")
    private List<Languages> languages;

    @SerializedName("flag")
    private String flag;

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

    public String getFlag() {
        return flag;
    }
}
