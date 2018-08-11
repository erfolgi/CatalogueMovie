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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import erfolgi.com.cataloguemovie.Adapter.SoonCardAdapter;
import erfolgi.com.cataloguemovie.Adapter.SoonMovieAdapter;
import erfolgi.com.cataloguemovie.BuildConfig;
import erfolgi.com.cataloguemovie.DetailActivity;
import erfolgi.com.cataloguemovie.Item.SoonItem;
import erfolgi.com.cataloguemovie.ItemClickSupport;
import erfolgi.com.cataloguemovie.Loader.ComingSoonLoader;

import erfolgi.com.cataloguemovie.NavBar;
import erfolgi.com.cataloguemovie.R;

public class ComingSoonFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<SoonItem>>{
    SoonMovieAdapter adapter;
    private ArrayList<SoonItem> mData = new ArrayList<>();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coming_soon, container, false);
        con = getActivity();
        adapter= new SoonMovieAdapter(Objects.requireNonNull(getActivity()));
        rvCategory = (RecyclerView)view.findViewById(R.id.rv_comingsoon);
        rvCategory.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
        this.mData=adapter.getmData();
        Bundle bundle = new Bundle();
//
        showRecyclerList();
        getActivity().getSupportLoaderManager().initLoader(0, bundle, this);

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
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_JUDUL, mData.get(position).getJuduls());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_DESC, mData.get(position).getDeskripsis());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_RILIS, mData.get(position).getRiliss());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_URL, mData.get(position).getGambars());
                Toast.makeText(getActivity(), mData.get(position).getJuduls(), Toast.LENGTH_SHORT).show();

                startActivity(moveWithDataIntent);
            }
        });
    }
    private void showRecyclerCardView(){
        rvCategory.setLayoutManager(new LinearLayoutManager(con));
        SoonCardAdapter cardView = new SoonCardAdapter(con);
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
    public Loader<ArrayList<SoonItem>> onCreateLoader(int id, @Nullable Bundle args) {

        if (args != null){//bb83bd5cf496014b1bf123c7b88809ad BuildConfig.API_KEY
            url="https://api.themoviedb.org/3/movie/upcoming?api_key="+  BuildConfig.API_KEY+"&language="+ con.getString(R.string.lang);
        }
        Toast.makeText(getActivity(), con.getString(R.string.wait), Toast.LENGTH_SHORT).show();
        return new ComingSoonLoader(con,url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SoonItem>> loader, ArrayList<SoonItem> data) {
        adapter.setData(data);
        Toast.makeText(getActivity(), con.getString(R.string.finished), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<SoonItem>> loader) {
        adapter.setData(null);
    }
}
