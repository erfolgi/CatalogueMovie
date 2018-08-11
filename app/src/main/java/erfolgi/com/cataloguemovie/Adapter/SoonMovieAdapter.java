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
import erfolgi.com.cataloguemovie.Item.SoonItem;
import erfolgi.com.cataloguemovie.R;

public class SoonMovieAdapter extends RecyclerView.Adapter<SoonMovieAdapter.CategoryViewHolder2> {
    private ArrayList<SoonItem> sData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public SoonMovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<SoonItem> items){

        sData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<SoonItem> getmData() {
        return sData;
    }



    @Override
    public CategoryViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = mInflater.inflate(R.layout.items_soon, parent, false);
        return new CategoryViewHolder2(itemRow);

    }

    @Override
    public void onBindViewHolder( CategoryViewHolder2 holders, int position) {

        holders.textViewJudul_s.setText(sData.get(position).getJuduls());
        holders.textViewDeskripsi_s.setText(sData.get(position).getDeskripsis());
        holders.textViewRilis_s.setText(sData.get(position).getRiliss());

        Glide.with(context)
                .load(sData.get(position).getGambars())
                .crossFade()
                .into(holders.imageViewGambar_s);
    }

    @Override
    public int getItemCount() {
        return sData.size();
    }



    class CategoryViewHolder2 extends RecyclerView.ViewHolder{
       @BindView(R.id.judul_s) TextView textViewJudul_s;
       @BindView(R.id.deskripsi_s) TextView textViewDeskripsi_s;
       @BindView(R.id.rilis_s) TextView textViewRilis_s;
       @BindView(R.id.gambar_s) ImageView imageViewGambar_s;

        CategoryViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
