package com.thisrahul.miskaatask.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.thisrahul.miskaatask.R;
import com.thisrahul.miskaatask.database.CountryEntity;
import com.thisrahul.miskaatask.databinding.RvCountriesItemLayoutBinding;
import com.thisrahul.miskaatask.model.CountryDetail;
import com.thisrahul.miskaatask.model.Languages;
import com.thisrahul.miskaatask.utils.Util;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder >{


    private Context context;
    private List<CountryDetail> countryDetailList;
    private List<CountryEntity> countryEntityList;
    private boolean isOnline;

    public CountryAdapter(Context context,List<CountryDetail> countryDetailList,boolean isOnline) {
        this.context = context;
        this.countryDetailList = countryDetailList;
        this.isOnline = isOnline;
    }

    public CountryAdapter(List<CountryEntity> countryEntityList,Context context, boolean isOnline) {
        this.context = context;
        this.countryEntityList = countryEntityList;
        this.isOnline = isOnline;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvCountriesItemLayoutBinding binding = RvCountriesItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        if (isOnline){
            CountryDetail countryDetail = countryDetailList.get(position);
            if (countryDetail.getName().equals("")) {
                holder.binding.txtCountry.setText("Not Available");
            }else {
                holder.binding.txtCountry.setText(countryDetail.getName());
            }

            if (countryDetail.getCapital().equals("")) {
                holder.binding.txtCapital.setText("Not Available");
            }else {
                holder.binding.txtCapital.setText(countryDetail.getCapital());
            }

            if (countryDetail.getName().equals("")) {
                holder.binding.txtCountry1.setText("Not Available");
            }else {
                holder.binding.txtCountry1.setText(countryDetail.getName());
            }
            if (countryDetail.getCapital().equals("")) {
                holder.binding.txtCapital1.setText("Not Available");
            }else {
                holder.binding.txtCapital1.setText(countryDetail.getCapital());
            }
            if (countryDetail.getRegion().equals("")){
                holder.binding.txtRegion.setText("Not Available");
            }else {
                holder.binding.txtRegion.setText(countryDetail.getRegion());
            }
            if (countryDetail.getSubRegion().equals("")){
                holder.binding.txtSubRegion.setText("Not Available");
            }else {
                holder.binding.txtSubRegion.setText(countryDetail.getSubRegion());
            }
            if (countryDetail.getPopulation().equals("")){
                holder.binding.txtPopulation.setText("Not Available");
            }else {
                holder.binding.txtPopulation.setText(countryDetail.getPopulation());
            }

            String border = "";
            for (String s : countryDetail.getBorders()){
                if (!border.equals("")) {
                    border = border + ","+s;
                }else {
                    border = border + s;
                }
            }
            if (border.equals("")){
                holder.binding.txtBorders.setText("Not Available");

            }else{
                holder.binding.txtBorders.setText(border);
            }

            String languages = "";
            for (Languages l : countryDetail.getLanguages()){
                if (!languages.equals("")){
                    languages = languages +","+l.getName();
                }else {
                    languages = languages + l.getName();
                }
            }
            if (languages.equals("")){
                holder.binding.txtLanguages.setText("Not Available");
            }else {
                holder.binding.txtLanguages.setText(languages);
            }
            Util.setImage(context,countryDetail.getFlag(),holder.binding.imgCountry);
        }else {
            CountryEntity countryEntity = countryEntityList.get(position);
            if (countryEntity.getName().equals("")) {
                holder.binding.txtCountry.setText("Not Available");
            }else {
                holder.binding.txtCountry.setText(countryEntity.getName());
            }

            if (countryEntity.getCapital().equals("")) {
                holder.binding.txtCapital.setText("Not Available");
            }else {
                holder.binding.txtCapital.setText(countryEntity.getCapital());
            }

            if (countryEntity.getName().equals("")) {
                holder.binding.txtCountry1.setText("Not Available");
            }else {
                holder.binding.txtCountry1.setText(countryEntity.getName());
            }
            if (countryEntity.getCapital().equals("")) {
                holder.binding.txtCapital1.setText("Not Available");
            }else {
                holder.binding.txtCapital1.setText(countryEntity.getCapital());
            }
            if (countryEntity.getRegion().equals("")){
                holder.binding.txtRegion.setText("Not Available");
            }else {
                holder.binding.txtRegion.setText(countryEntity.getRegion());
            }
            if (countryEntity.getSubRegion().equals("")){
                holder.binding.txtSubRegion.setText("Not Available");
            }else {
                holder.binding.txtSubRegion.setText(countryEntity.getSubRegion());
            }
            if (countryEntity.getPopulation().equals("")){
                holder.binding.txtPopulation.setText("Not Available");
            }else {
                holder.binding.txtPopulation.setText(countryEntity.getPopulation());
            }

            String border = "";
            for (String s : countryEntity.getBorders()){
                if (!border.equals("")) {
                    border = border + ","+s;
                }else {
                    border = border + s;
                }
            }
            if (border.equals("")){
                holder.binding.txtBorders.setText("Not Available");

            }else{
                holder.binding.txtBorders.setText(border);
            }

            String languages = "";
            for (Languages l : countryEntity.getLanguages()){
                if (!languages.equals("")){
                    languages = languages +","+l.getName();
                }else {
                    languages = languages + l.getName();
                }
            }
            if (languages.equals("")){
                holder.binding.txtLanguages.setText("Not Available");
            }else {
                holder.binding.txtLanguages.setText(languages);
            }

            File imgFile = new File(countryEntity.getFlags());
            if (imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.binding.imgCountry.setImageBitmap(myBitmap);
                Toast.makeText(context, ""+imgFile, Toast.LENGTH_SHORT).show();
            }
        }
        holder.itemView.setOnClickListener(v ->{
                if (holder.binding.layouExpandable.getVisibility() == View.GONE) {
                    holder.binding.imgExpColl.setImageResource(R.drawable.ic_expand_less);
                   // TransitionManager.beginDelayedTransition(holder.binding.getRoot(), new AutoTransition());
                    holder.binding.layouExpandable.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.imgExpColl.setImageResource(R.drawable.ic_expand_more);
                  //  TransitionManager.beginDelayedTransition(holder.binding.getRoot(), new AutoTransition());
                    holder.binding.layouExpandable.setVisibility(View.GONE);
                }
        });
    }

    @Override
    public int getItemCount() {
        if (isOnline){
            try {
                return countryDetailList.size();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {
                return countryEntityList.size();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder{

        RvCountriesItemLayoutBinding binding;

        public CountryViewHolder(@NonNull RvCountriesItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
