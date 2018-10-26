package erfolgi.com.cataloguemovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import erfolgi.com.cataloguemovie.CustomOnItemClickListener;
import erfolgi.com.cataloguemovie.DetailActivity;
import erfolgi.com.cataloguemovie.Entity.Favorite;
import erfolgi.com.cataloguemovie.R;

public class FavCardAdapter extends RecyclerView.Adapter<FavCardAdapter.CardViewViewHolder>{
    private ArrayList<Favorite> listFilm;
    private Context context;

    public FavCardAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Favorite> getListFilm() {
        return listFilm;
    }

    public void setListFilm(ArrayList<Favorite> listFilm) {
        this.listFilm = listFilm;
    }

    @Override
    public FavCardAdapter.CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cardview, parent, false);
        return new FavCardAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavCardAdapter.CardViewViewHolder holder, int position) {

        holder.Judul.setText(listFilm.get(position).getTitle());
        holder.Deskripsi.setText(listFilm.get(position).getDescription());
        holder.Rilis.setText(listFilm.get(position).getDate());

        Glide.with(context)
                .load(listFilm.get(position).getImage())
                .into(holder.Gambar);


        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, getListFilm().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent moveWithDataIntent = new Intent(context, DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_JUDUL, getListFilm().get(position).getTitle());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_DESC, getListFilm().get(position).getDescription());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_RILIS, getListFilm().get(position).getDate());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_URL, getListFilm().get(position).getImage());
                context.startActivity(moveWithDataIntent);

            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share "+getListFilm().get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getListFilm().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_judul)
        TextView Judul;
        @BindView(R.id.card_desk)TextView Deskripsi;
        @BindView(R.id.card_rilis)TextView Rilis;
        @BindView(R.id.card_poster)ImageView Gambar;
        @BindView(R.id.btn_set_detail)Button btnDetail;
        @BindView(R.id.btn_set_share)Button btnShare;

        CardViewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}