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
import erfolgi.com.cataloguemovie.Item.SearchItem;
import erfolgi.com.cataloguemovie.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CategoryViewHolder3> {
    private ArrayList<SearchItem> sData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<SearchItem> items){
        sData.clear();
        sData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<SearchItem> getmData() {
        return sData;
    }

    @NonNull
    @Override
    public CategoryViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = mInflater.inflate(R.layout.items_search, parent, false);
        return new CategoryViewHolder3(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder3 holders, int position) {
        holders.textViewJudul_f.setText(sData.get(position).getJudulf());
        holders.textViewDeskripsi_f.setText(sData.get(position).getDeskripsif());
        holders.textViewRilis_f.setText(sData.get(position).getRilisf());

        Glide.with(context)
                .load(sData.get(position).getGambarf())
                .into(holders.imageViewGambar_f);
    }

    @Override
    public int getItemCount() {
        return sData.size();
    }

    public class CategoryViewHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.judul_f) TextView textViewJudul_f;
        @BindView(R.id.deskripsi_f) TextView textViewDeskripsi_f;
        @BindView(R.id.rilis_f) TextView textViewRilis_f;
        @BindView(R.id.gambar_f) ImageView imageViewGambar_f;
        public CategoryViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
