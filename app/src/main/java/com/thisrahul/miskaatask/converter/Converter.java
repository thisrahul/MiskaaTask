package com.thisrahul.miskaatask.converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thisrahul.miskaatask.model.Languages;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

//    @TypeConverter
//    public static List<Languages> fromValue(String value) {
//        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
//        return new Gson().fromJson(value, listType);
//    }
//
//    @TypeConverter
//    public static String fromArrayList(List<Languages> list) {
//        Gson gson = new Gson();
//        return gson.toJson(list);
//    }

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @TypeConverter
    public static Bitmap toBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @TypeConverter
    public String fromOptionValuesList(List<Languages> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Languages>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<Languages> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Languages>>() {
        }.getType();
        List<Languages> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
