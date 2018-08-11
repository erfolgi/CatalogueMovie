package erfolgi.com.myfavoritemovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import butterknife.BindView;
import erfolgi.com.myfavoritemovie.adapter.FavAdapter;
import erfolgi.com.myfavoritemovie.db.DatabaseContract;

import static erfolgi.com.myfavoritemovie.db.DatabaseContract.CONTENT_URI;
import static erfolgi.com.myfavoritemovie.db.DatabaseContract.getColumnString;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener{

    private FavAdapter favAdapter;
    ListView lvFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My Favorite Movie");

        lvFav = (ListView)findViewById(R.id.LV);
        favAdapter = new FavAdapter(this, null, true);
        lvFav.setAdapter(favAdapter);
        lvFav.setOnItemClickListener(this);
//
       getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
       getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("", "onCreateLoader: ");
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
       // return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favAdapter.swapCursor(data);
        Log.d("", "onLoadFinished: ");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) favAdapter.getItem(i);


        Intent intent = new Intent(MainActivity.this
                , DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_JUDUL, (getColumnString(cursor, DatabaseContract.NoteColumns.TITLE)));
        intent.putExtra(DetailActivity.EXTRA_DESC, (getColumnString(cursor, DatabaseContract.NoteColumns.DESCRIPTION)));
        intent.putExtra(DetailActivity.EXTRA_RILIS,(getColumnString(cursor, DatabaseContract.NoteColumns.DATE)));
        intent.putExtra(DetailActivity.EXTRA_URL, (getColumnString(cursor, DatabaseContract.NoteColumns.IMAGE)));

        startActivity(intent);
    }
}
