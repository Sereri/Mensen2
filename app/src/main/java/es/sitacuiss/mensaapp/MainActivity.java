package es.sitacuiss.mensaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Main activity containing a ViewPager (with PagerTabStrip). The MainActivity splits the list of days
 * the FullscreenActivity parsed into seperate dates and injects this into the viewpager adapter.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ArrayList<String> dayList = getIntent().getExtras().getStringArrayList("Days");
        Mensa StartMensa = Mensa.getMensa(prefs.getInt("start_mensa", Mensa.getValue(Mensa.HOCHSCHULE_MANNHEIM)));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Util.getTitle(StartMensa, this));

        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        MensaFragmentAdapter adapter = new MensaFragmentAdapter(this);
        mViewPager.setAdapter(adapter);

        // parse days and instantiate fragments
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy_MM_dd");
        DateTimeFormatter parsingDTF = DateTimeFormat.forPattern("EEE, dd.MM.yyyy");
        DateTimeFormatter out = DateTimeFormat.forPattern("EE, dd.MM.yy");
        if (dayList != null && !dayList.isEmpty()) {
            for (String day : dayList) {
                day = day.replace("\u00a0", "");
                LocalDate dayDate = LocalDate.parse(day.trim(), parsingDTF.withLocale(Locale.GERMAN));
                if (dayDate.isBefore(LocalDate.now())) {
                    continue;
                }
                Bundle bundle = new Bundle();
                bundle.putString("College", Util.getURL(StartMensa));
                bundle.putString("Day", dayDate.toString(fmt));
                MensaFragment todayFrag = (MensaFragment) MensaFragment.instantiate(this, MensaFragment.class.getName(), bundle);

                ((MensaFragmentAdapter) mViewPager.getAdapter()).addPage(todayFrag, dayDate.toString(out));
            }

        }
        mViewPager.getAdapter().notifyDataSetChanged();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(Util.getNavItem(StartMensa));
    }

    @Override
    protected void onResume() {
        super.onResume();
        int current = mViewPager.getCurrentItem();
        // remove all fragments/tabs with past days
        ArrayList<String> days = ((MensaFragmentAdapter) mViewPager.getAdapter()).getTitles();
        ArrayList<String> removeDays = new ArrayList<>();
        for (String day : days) {
            DateTimeFormatter out = DateTimeFormat.forPattern("EE, dd.MM.yy");
            LocalDate dayDate = out.parseLocalDate(day.trim());
            if (dayDate.isBefore(LocalDate.now())) {
                removeDays.add(day);
            }
        }

        if (!removeDays.isEmpty()) {
            ((MensaFragmentAdapter) mViewPager.getAdapter()).removePages(removeDays);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mViewPager.getAdapter() != null) {
            MensaFragmentAdapter adapter = (MensaFragmentAdapter) mViewPager.getAdapter();
            if (id == R.id.college_hsma) {
                adapter.changeCollege(Util.HOCHSCHULE_MANNHEIM_URL);
                getSupportActionBar().setTitle(R.string.hsma);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("start_mensa", Mensa.getValue(Mensa.HOCHSCHULE_MANNHEIM)).commit();
            } else if (id == R.id.college_mas) {
                adapter.changeCollege(Util.UNI_MANNHEIM_URL);
                getSupportActionBar().setTitle(R.string.mas);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("start_mensa", Mensa.getValue(Mensa.MENSA_AM_SCHLOSS)).commit();
            } else if (id == R.id.college_mhs) {
                adapter.changeCollege(Util.CAFETERIA_MUSIKHOCHSCHULE_URL);
                getSupportActionBar().setTitle(R.string.mhs);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("start_mensa", Mensa.getValue(Mensa.CAFETERIA_MUSIKHOCHSCHULE)).commit();
            } else if (id == R.id.college_kubus) {
                adapter.changeCollege(Util.CAFETERIA_KUBUS_URL);
                getSupportActionBar().setTitle(R.string.kubus);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("start_mensa", Mensa.getValue(Mensa.CAFETERIA_KUBUS)).commit();
            } else if (id == R.id.college_wglg) {
                adapter.changeCollege(Util.MENSARIA_WOHLGELEGEN_URL);
                getSupportActionBar().setTitle(R.string.wglg);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("start_mensa", Mensa.getValue(Mensa.MENSARIA_WOHLGELEGEN)).commit();
            } else if (id == R.id.college_erhf) {
                adapter.changeCollege(Util.SCHLOSS_EHRENHOF_URL);
                getSupportActionBar().setTitle(R.string.eo);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("start_mensa", Mensa.getValue(Mensa.SCHLOSS_EHRENHOF)).commit();
            } else if (id == R.id.college_mtrp) {
                adapter.changeCollege(Util.MENSARIA_METROPOL_URL);
                getSupportActionBar().setTitle(R.string.mtpl);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("start_mensa", Mensa.getValue(Mensa.MENSARIA_METROPOL)).commit();
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.go_to_today) {
            mViewPager.setCurrentItem(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
