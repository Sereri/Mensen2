package es.sitacuiss.mensaapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * FragmentAdapter for MensaFragments
 */
class MensaFragmentAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "MensaFragmentAdapter";

    private final ArrayList<MensaFragment> mTab = new ArrayList<>();
    private final ArrayList<String> mTitles = new ArrayList<>();

    public MensaFragmentAdapter (AppCompatActivity activity){
        super(activity.getSupportFragmentManager());
    }

    public Fragment getItem(int position) {
        if(mTab.size() > 0){
            return mTab.get(position);
        }
        return null;
    }

    public int getCount() {
        return mTab.size();
    }

    public void addPage(MensaFragment frag, String day) {
        mTab.add(frag);
        mTitles.add(day);
        notifyDataSetChanged();
    }

    public void removePages(ArrayList<String> pages) {

        for(String page: pages){
            int index = mTitles.indexOf(page);
            mTab.remove(index);
            mTitles.remove(index);
        }
        notifyDataSetChanged();
    }

    public void changeCollege(String College){
        for(MensaFragment frag : mTab) {
            if(frag.isAdded()){
                frag.changeCollege(College);
            }
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return FragmentPagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public ArrayList<String> getTitles () { return mTitles;}
}
