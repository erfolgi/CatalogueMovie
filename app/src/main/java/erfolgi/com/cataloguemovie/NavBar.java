package erfolgi.com.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import erfolgi.com.cataloguemovie.DB.FavHelper;
import erfolgi.com.cataloguemovie.Fragment.FavoriteFragment;
import erfolgi.com.cataloguemovie.Fragment.MoviesFragment;
import erfolgi.com.cataloguemovie.Fragment.SearchFragment;

public class NavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FavHelper fav = new FavHelper(this);
//        fav.open();
//        fav.droptab();
//        fav.close();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        MoviesFragment mHomeFragment = new MoviesFragment();
        mFragmentTransaction.replace(R.id.main_container_wrapper, mHomeFragment, MoviesFragment.class.getSimpleName());
        Log.d("MyFlexibleFragment", "Fragment Name: "+MoviesFragment.class.getSimpleName());
        mFragmentTransaction.commit();
        navigationView.setCheckedItem(R.id.nav_movies);
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_movies) {

            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            MoviesFragment mHomeFragment = new MoviesFragment();
            mFragmentTransaction.replace(R.id.main_container_wrapper, mHomeFragment, MoviesFragment.class.getSimpleName());
            Log.d("MyFlexibleFragment", "Fragment Name: "+MoviesFragment.class.getSimpleName());
            mFragmentTransaction.commit();

        } else if (id == R.id.nav_search) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            SearchFragment searchFragment = new SearchFragment();
            mFragmentTransaction.replace(R.id.main_container_wrapper, searchFragment, MoviesFragment.class.getSimpleName());

            mFragmentTransaction.commit();
        } else if (id == R.id.nav_setting) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);

        } else if (id==R.id.nav_favorite){
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            FavoriteFragment FF = new FavoriteFragment();
            mFragmentTransaction.replace(R.id.main_container_wrapper, FF, MoviesFragment.class.getSimpleName());

            mFragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
