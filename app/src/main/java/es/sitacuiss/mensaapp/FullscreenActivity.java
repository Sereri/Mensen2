package es.sitacuiss.mensaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Fullscreen activity that loads the list of days from the stw-ma website
 */
public class FullscreenActivity extends AppCompatActivity {
    private static final String TAG = "FullscreenActivity";

    private final Context self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_fullscreen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        OkHttpClient client = Util.getClient(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultUrl = Util.getURL(Mensa.getMensa(prefs.getInt("start_mensa", Mensa.getValue(Mensa.HOCHSCHULE_MANNHEIM))));
        Request request = new Request.Builder().url(defaultUrl+".html").cacheControl(CacheControl.FORCE_NETWORK).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "Request Failed",e);
                finish();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("FullscreenActivity", "Request Success");
                ArrayList<String> days = MenuParser.getInstance().parseSelectField(response.body().string());
                Intent intent = new Intent(self, MainActivity.class);
                intent.putExtra("Days", days);
                startActivity(intent);
                finish();
            }
        });
    }


}
