package erfolgi.com.cataloguemovie.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import erfolgi.com.cataloguemovie.Fragment.ComingSoonFragment;
import erfolgi.com.cataloguemovie.Fragment.NowPlayingFragment;
import erfolgi.com.cataloguemovie.R;

import static java.security.AccessController.getContext;

public class FragmentTabAdapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 2;
    Context con;
    public FragmentTabAdapter(FragmentManager fm, Context con) {
        super(fm);
        this.con=con;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new ComingSoonFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return con.getString(R.string.Now_Playing);

            case 1:
                return con.getString(R.string.Coming_Soon);

        }
        return null;
    }
}
