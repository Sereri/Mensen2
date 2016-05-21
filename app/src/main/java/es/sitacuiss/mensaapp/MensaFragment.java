package es.sitacuiss.mensaapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Mensa Fragment containing a RecyclerView displaying the menus of the day
 */
public class MensaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "MensaFragment";

    private RecyclerView menuRecyclerView;
    private boolean dataLoaded;
    private ArrayList<Menu> menuList = new ArrayList<>();
    private String day = "1970_1_1";
    private String college = "";
    private SwipeRefreshLayout SRL;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        college = getArguments().getString("College");
        day = getArguments().getString("Day");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

        View view = inflater.inflate(R.layout.mensa_fragment, container, false);

        SRL = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        SRL.setOnRefreshListener(this);
        SRL.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        if (!dataLoaded) {
            getData();
        }

        menuRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        menuRecyclerView.setAdapter(new MenuAdapter(this));

        return view;
    }

    private void getData() {
        getData(false);
    }

    /**
     * Gets and parses the menus for the day
     *
     * @param forceNetwork if true, ignore cache
     */
    private void getData(boolean forceNetwork) {
        Request.Builder requestBuilder = new Request.Builder().url(college + "-date-" + day + ".html");
        if (forceNetwork) {
            requestBuilder.cacheControl(CacheControl.FORCE_NETWORK);
        }
        Request request = requestBuilder.build();

        Call call = Util.getClient(getContext()).newCall(request);
        SRL.post(new Runnable() {
            @Override
            public void run() {
                if (!dataLoaded) {
                    SRL.setRefreshing(true);
                }
            }
        });
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Request request, IOException e) {
                             Log.e("MensaFragment", "Request Failed: " + request.url());
                             SRL.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     getActivity().runOnUiThread(new Runnable() {
                                         public void run() {
                                             SRL.setRefreshing(false);
                                         }
                                     });
                                 }
                             });
                         }

                         @Override
                         public void onResponse(Response response) throws IOException {
                             Log.i("MensaFragment", "Request Success: " + response.request().url());
                             final ArrayList<Menu> responseMenus = MenuParser.getInstance().parseMenu(response.body().string());
                             if (responseMenus.isEmpty()) {
                                 responseMenus.add(new Menu(getString(R.string.no_data_available)));
                             }
                             if (getActivity() != null) {
                                 getActivity().runOnUiThread(new Runnable() {
                                     public void run() {
                                         menuList.clear();
                                         menuRecyclerView.getAdapter().notifyDataSetChanged();
                                         menuList = responseMenus;
                                         menuRecyclerView.getAdapter().notifyDataSetChanged();
                                         SRL.setRefreshing(false);
                                     }
                                 });
                             }
                             dataLoaded = true;
                         }
                     }

        );
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    public void changeCollege(String College) {
        dataLoaded = false;
        this.college = College;
        getData();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        menuRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public ArrayList<Menu> getMenuList() {
        return menuList;
    }
}
