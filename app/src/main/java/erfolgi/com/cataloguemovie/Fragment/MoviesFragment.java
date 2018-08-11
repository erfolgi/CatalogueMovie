package erfolgi.com.cataloguemovie.Fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import erfolgi.com.cataloguemovie.Adapter.FragmentTabAdapter;
import erfolgi.com.cataloguemovie.NavBar;
import erfolgi.com.cataloguemovie.R;


public class MoviesFragment extends Fragment {
TabLayout tabLayout;
ViewPager viewPager;
Context con;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((NavBar) getActivity())
                .setActionBarTitle(getString(R.string.movies));
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        con=getActivity();
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new FragmentTabAdapter(getChildFragmentManager(),con));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


}
