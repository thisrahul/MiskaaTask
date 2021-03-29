package com.thisrahul.miskaatask.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.pixplicity.sharp.Sharp;
import com.thisrahul.miskaatask.R;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Util {
    private static OkHttpClient httpClient;

    public static void setImage(Context context, final ImageView target, String url) {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFailure(Call call, IOException e) {
                target.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_launcher_background));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    InputStream stream = response.body().byteStream();
                    Sharp.loadInputStream(stream).into(target);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }


    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        return progressDialog;
    }
}
