package erfolgi.com.cataloguemovie.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import erfolgi.com.cataloguemovie.Adapter.FavAdapter;
import erfolgi.com.cataloguemovie.DB.FavHelper;
import erfolgi.com.cataloguemovie.DetailActivity;
import erfolgi.com.cataloguemovie.Entity.Favorite;
import erfolgi.com.cataloguemovie.ItemClickSupport;
import erfolgi.com.cataloguemovie.NavBar;
import erfolgi.com.cataloguemovie.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    @BindView(R.id.recyclerview_fav)RecyclerView recyclerView;
    FavAdapter favAdapter;
    FavHelper favHelper;
    Context con;
    @BindView(R.id.SearchView)SearchView SW;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((NavBar) getActivity())
                .setActionBarTitle(getString(R.string.favorite));
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        con = getActivity();
        favHelper = new FavHelper(con);
        favAdapter = new FavAdapter(con);


        recyclerView.setLayoutManager(new LinearLayoutManager(con));

        recyclerView.setAdapter(favAdapter);

        favHelper.open();
        final ArrayList<Favorite> fav = favHelper.query();
        for (int i=0 ; i<fav.size();i++){
            Log.d("->JUDUL", fav.get(i).getTitle());
            Log.d("->DESKRIPSI", fav.get(i).getDescription());
            Log.d("->DATE", fav.get(i).getDate());
//            Log.d("->LINK", fav.get(i).getImage());
            Log.d("->", "");
        }
        favHelper.close();
        favAdapter.addItem(fav);

        showRecyclerList(fav);
        // Search View
        SW.setQueryHint(getResources().getString(R.string.masukkan_nama_film));
        SW.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                favHelper.open();
                ArrayList<Favorite> fav = favHelper.getDataByName(query);
                favHelper.close();
                favAdapter.addItem(fav);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                favHelper.open();
                ArrayList<Favorite> fav1 = favHelper.getDataByName(newText);
                Log.d("-> >", String.valueOf(fav1));
                favHelper.close();
                favAdapter.addItem(fav1);
                showRecyclerList(fav1);
                return false;
            }
        });
        return view;
    }
    private void showRecyclerList(final ArrayList<Favorite> fav) {
        recyclerView.setLayoutManager(new LinearLayoutManager(con));

        favAdapter.addItem(fav);
        recyclerView.setAdapter(favAdapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_JUDUL, fav.get(position).getTitle());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_DESC, fav.get(position).getDescription());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_RILIS, fav.get(position).getDate());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_URL, fav.get(position).getImage());
                Toast.makeText(getActivity(), fav.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                getActivity().startActivity(moveWithDataIntent);
            }
        });
       // return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favHelper.open();
        final ArrayList<Favorite> fav = favHelper.query();
        for (int i=0 ; i<fav.size();i++){
            Log.d("->JUDUL", fav.get(i).getTitle());
            Log.d("->DESKRIPSI", fav.get(i).getDescription());
            Log.d("->DATE", fav.get(i).getDate());
//            Log.d("->LINK", fav.get(i).getImage());
            Log.d("->", "");
        }
        favHelper.close();

        favAdapter.addItem(fav);
    }
}
