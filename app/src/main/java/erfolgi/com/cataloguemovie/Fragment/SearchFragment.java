package erfolgi.com.cataloguemovie.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.util.TextUtils;
import erfolgi.com.cataloguemovie.Adapter.SearchAdapter;
import erfolgi.com.cataloguemovie.Adapter.SearchCardAdapter;
import erfolgi.com.cataloguemovie.Adapter.SoonCardAdapter;
import erfolgi.com.cataloguemovie.BuildConfig;
import erfolgi.com.cataloguemovie.DetailActivity;
import erfolgi.com.cataloguemovie.Item.SearchItem;
import erfolgi.com.cataloguemovie.ItemClickSupport;
import erfolgi.com.cataloguemovie.Loader.SearchLoader;
import erfolgi.com.cataloguemovie.NavBar;
import erfolgi.com.cataloguemovie.R;


public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<SearchItem>>{
    SearchAdapter adapter;
    @BindView(R.id.search_page)LinearLayout mainLayout;
    @BindView(R.id.SV)SearchView SV;
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    private ArrayList<SearchItem> mData = new ArrayList<>();
    Context con;
    String url ;
    RecyclerView rvCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((NavBar) getActivity())
                .setActionBarTitle(getString(R.string.search));
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        con = getActivity();
        adapter= new SearchAdapter(con);
        adapter.notifyDataSetChanged();
        rvCategory = (RecyclerView)view.findViewById(R.id.rv_searching);
        rvCategory.setHasFixedSize(true);
        ///

        ButterKnife.bind(this, view);

        // Search View
        SV.setQueryHint(getResources().getString(R.string.masukkan_nama_film));
        SV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String newText) {
                if (TextUtils.isEmpty(newText))return true;

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_FILM,newText);
                load(newText);
                Toast.makeText(getActivity(), con.getString(R.string.wait), Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager)con.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(),0);
                showRecyclerList();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //KOSONG
                return false;
            }
        });
        // End Search View
        this.mData=adapter.getmData();
        showRecyclerList();

        return view;
    }
    public void load (String film){

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM,film);
        getActivity().getSupportLoaderManager().restartLoader(2, bundle, this);
    }

    private void showRecyclerList() {
        rvCategory.setLayoutManager(new LinearLayoutManager(con));

        adapter.setData(mData);
        rvCategory.setAdapter(adapter);
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_JUDUL, mData.get(position).getJudulf());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_DESC, mData.get(position).getDeskripsif());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_RILIS, mData.get(position).getRilisf());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_URL, mData.get(position).getGambarf());
                Toast.makeText(getActivity(), mData.get(position).getJudulf(), Toast.LENGTH_SHORT).show();

                startActivity(moveWithDataIntent);
            }
        });
    }
    private void showRecyclerCardView(){
        rvCategory.setLayoutManager(new LinearLayoutManager(con));
        SearchCardAdapter cardView = new SearchCardAdapter(con);
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
    public Loader<ArrayList<SearchItem>> onCreateLoader(int id, @Nullable Bundle args) {
        String kumpulanFilm = "";
        if (args != null){
            kumpulanFilm = args.getString(EXTRAS_FILM);
            url="https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.API_KEY+"&language="+ con.getString(R.string.lang)+"&query="+kumpulanFilm;
        }

        return new SearchLoader(con,url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SearchItem>> loader, ArrayList<SearchItem> data) {
        adapter.setData(data);
        Toast.makeText(getActivity(), con.getString(R.string.finished), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<SearchItem>> loader) {
        adapter.setData(null);
    }
}
