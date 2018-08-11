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
import erfolgi.com.cataloguemovie.Item.SearchItem;
import erfolgi.com.cataloguemovie.R;

public class SearchCardAdapter extends RecyclerView.Adapter<SearchCardAdapter.CardViewViewHolder>{
    private ArrayList<SearchItem> listFilm;
    private Context context;

    public SearchCardAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<SearchItem> getListFilm() {
        return listFilm;
    }

    public void setListFilm(ArrayList<SearchItem> listFilm) {
        this.listFilm = listFilm;
    }

    @Override
    public SearchCardAdapter.CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cardview, parent, false);
        return new SearchCardAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchCardAdapter.CardViewViewHolder holder, int position) {

        SearchItem p = getListFilm().get(position);

        Glide.with(context)
                .load(p.getGambarf())
                .into(holder.imageViewGambar);

        holder.textViewJudul.setText(p.getJudulf());
        holder.textViewDeskripsi.setText(p.getDeskripsif());
        holder.textViewRilis.setText(p.getRilisf());


        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, getListFilm().get(position).getJudulf(), Toast.LENGTH_SHORT).show();
                Intent moveWithDataIntent = new Intent(context, DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_JUDUL, getListFilm().get(position).getJudulf());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_DESC, getListFilm().get(position).getDeskripsif());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_RILIS, getListFilm().get(position).getRilisf());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_URL, getListFilm().get(position).getGambarf());
                Toast.makeText(context, getListFilm().get(position).getJudulf(), Toast.LENGTH_SHORT).show();
                context.startActivity(moveWithDataIntent);

            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share "+getListFilm().get(position).getJudulf(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getListFilm().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_judul)
        TextView textViewJudul;
        @BindView(R.id.card_desk)TextView textViewDeskripsi;
        @BindView(R.id.card_rilis)TextView textViewRilis;
        @BindView(R.id.card_poster)ImageView imageViewGambar;
        @BindView(R.id.btn_set_detail)Button btnDetail;
        @BindView(R.id.btn_set_share)Button btnShare;
        CardViewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
