package erfolgi.com.cataloguemovie.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import erfolgi.com.cataloguemovie.Adapter.NewCardViewAdapter;
import erfolgi.com.cataloguemovie.Adapter.NewMovieAdapter;
import erfolgi.com.cataloguemovie.BuildConfig;
import erfolgi.com.cataloguemovie.DetailActivity;
import erfolgi.com.cataloguemovie.ItemClickSupport;
import erfolgi.com.cataloguemovie.Item.MovieItems;
import erfolgi.com.cataloguemovie.NavBar;
import erfolgi.com.cataloguemovie.R;
import erfolgi.com.cataloguemovie.Loader.TheAsyncTaskLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    NewMovieAdapter adapter;
    private ArrayList<MovieItems> mData = new ArrayList<>();
    Context con;
    String url ;
    RecyclerView rvCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        con = getActivity();
        adapter= new NewMovieAdapter(con);
        rvCategory = (RecyclerView)view.findViewById(R.id.rv_nowplaying);
        rvCategory.setHasFixedSize(true);

        Bundle bundle = new Bundle();
        adapter.notifyDataSetChanged();
        this.mData=adapter.getmData();
        showRecyclerList();
        getActivity().getSupportLoaderManager().initLoader(1, bundle, this);

        return view;
    }

    private void showRecyclerList() {
        rvCategory.setLayoutManager(new LinearLayoutManager(con));
        adapter.setData(mData);
        rvCategory.setAdapter(adapter);
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_JUDUL, mData.get(position).getJudul());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_DESC, mData.get(position).getDeskripsi());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_RILIS, mData.get(position).getRilis());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_URL, mData.get(position).getGambar());
                Toast.makeText(getActivity(), mData.get(position).getJudul(), Toast.LENGTH_SHORT).show();
                getActivity().startActivity(moveWithDataIntent);
            }
        });

    }
    private void showRecyclerCardView(){
        rvCategory.setLayoutManager(new LinearLayoutManager(con));
        NewCardViewAdapter cardView = new NewCardViewAdapter(con);
        cardView.setListFilm(mData);
        rvCategory.setAdapter(cardView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_ListV:
                Toast.makeText(getActivity(), "List View", Toast.LENGTH_SHORT).show();
                showRecyclerList();

                break;
            case R.id.action_CV:
                Toast.makeText(getActivity(), "Card View", Toast.LENGTH_SHORT).show();
                showRecyclerCardView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {


        url="https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.API_KEY+"&language="+ con.getString(R.string.lang);
        Toast.makeText(getActivity(), con.getString(R.string.wait), Toast.LENGTH_SHORT).show();
        return new TheAsyncTaskLoader(con,url);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
        //Toast.makeText(getActivity(), con.getString(R.string.finished), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        adapter.setData(null);
    }


}
