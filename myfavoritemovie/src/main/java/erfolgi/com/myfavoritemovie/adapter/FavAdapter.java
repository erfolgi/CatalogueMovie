package erfolgi.com.myfavoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import erfolgi.com.myfavoritemovie.LoadGambar;
import erfolgi.com.myfavoritemovie.R;
import erfolgi.com.myfavoritemovie.entity.Favorite;

import static erfolgi.com.myfavoritemovie.db.DatabaseContract.NoteColumns.DATE;
import static erfolgi.com.myfavoritemovie.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static erfolgi.com.myfavoritemovie.db.DatabaseContract.NoteColumns.IMAGE;
import static erfolgi.com.myfavoritemovie.db.DatabaseContract.NoteColumns.TITLE;
import static erfolgi.com.myfavoritemovie.db.DatabaseContract.getColumnString;

public class FavAdapter extends CursorAdapter {
    private ArrayList<Favorite> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    @BindView(R.id.judul)
    TextView Judul;
    @BindView(R.id.deskripsi) TextView Deskripsi;
    @BindView(R.id.rilis) TextView Rilis;
    @BindView(R.id.gambar)
    ImageView Gambar;

    public FavAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_movie, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ButterKnife.bind(this,view);
        Judul.setText(getColumnString(cursor,TITLE));
        Deskripsi.setText(getColumnString(cursor,DESCRIPTION));
        Rilis.setText(getColumnString(cursor,DATE));
        String image = (getColumnString(cursor,IMAGE));
        Log.d("LINK: ", image);
        new LoadGambar(Gambar).execute(image);
    }

}