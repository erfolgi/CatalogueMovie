package erfolgi.com.cataloguemovie.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import erfolgi.com.cataloguemovie.Item.MovieItems;
import erfolgi.com.cataloguemovie.R;

public class NewMovieAdapter extends RecyclerView.Adapter<NewMovieAdapter.CategoryViewHolder>{
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public NewMovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setData(ArrayList<MovieItems> items){

        mData.addAll(items);
        notifyDataSetChanged();
    }


    public ArrayList<MovieItems> getmData() {
        return mData;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = mInflater.inflate(R.layout.items_movie, parent, false);
        return new CategoryViewHolder(itemRow);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.textViewJudul.setText(mData.get(position).getJudul());
        holder.textViewDeskripsi.setText(mData.get(position).getDeskripsi());
        holder.textViewRilis.setText(mData.get(position).getRilis());

        Glide.with(context)
                .load(mData.get(position).getGambar())
                .crossFade()
                .into(holder.imageViewGambar);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    class CategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.judul) TextView textViewJudul;
        @BindView(R.id.deskripsi) TextView textViewDeskripsi;
        @BindView(R.id.rilis) TextView textViewRilis;
        @BindView(R.id.gambar) ImageView imageViewGambar;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
