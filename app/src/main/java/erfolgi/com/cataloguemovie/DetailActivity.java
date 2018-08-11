package erfolgi.com.cataloguemovie;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import erfolgi.com.cataloguemovie.DB.FavHelper;
import erfolgi.com.cataloguemovie.Entity.Favorite;

import static erfolgi.com.cataloguemovie.DB.DatabaseContract.CONTENT_URI;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.DATE;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.DESCRIPTION;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.IMAGE;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.TITLE;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_JUDUL="judulnya";
    public static String EXTRA_DESC = "deskripsinya";
    public static String EXTRA_RILIS= "tanggalnya";
    public static String EXTRA_URL= "Gambarnya";

    FavHelper favHelper = new FavHelper(this);


    @BindView(R.id.detail_judul) TextView judul;
    @BindView(R.id.detail_deskripsi) TextView desc;
    @BindView(R.id.detail_rilis) TextView rilis;
    @BindView(R.id.detail_gambar) ImageView gambar;
    @BindView(R.id.btn_detail) Button btn;
    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ButterKnife.bind(this);
        icon = findViewById(R.id.fav_icon);
        EXTRA_JUDUL=getIntent().getStringExtra(EXTRA_JUDUL);
        EXTRA_DESC=getIntent().getStringExtra(EXTRA_DESC);
        EXTRA_RILIS=getIntent().getStringExtra(EXTRA_RILIS);
        EXTRA_URL=getIntent().getStringExtra(EXTRA_URL);
        getSupportActionBar().setTitle(EXTRA_JUDUL);
        String NEW_URL = EXTRA_URL.replace("/w185/", "/w780/"); //ganti gambar yang besar
        judul.setText(EXTRA_JUDUL);
        desc.setText(EXTRA_DESC);
        rilis.setText(EXTRA_RILIS);
       new LoadGambar(gambar).execute(NEW_URL); //Tidak bisa menggunakan Glide. Gambar tidak muncul


//        Glide.with(DetailActivity.this). //Tidak muncul
//                load(NEW_URL)
//                .into(gambar);

        favHelper.open();
        boolean checked = favHelper.check(EXTRA_JUDUL);
        favHelper.close();
        if(checked){
            icon.setImageResource(R.drawable.ic_favorite_yes);
        }else{
            icon.setImageResource(R.drawable.ic_favorite_no);
        }

        icon.setOnClickListener(this);

    }
    @OnClick(R.id.btn_detail)
    public void Submit(View view) {
        Toast.makeText(this, "Download sendiri dong :P", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fav_icon){
            favHelper.open();
            try {

                boolean checked = favHelper.check(EXTRA_JUDUL);

                if(checked){

                favHelper.delete(EXTRA_JUDUL);
                favHelper.deleteProvider(EXTRA_JUDUL);

                    Toast.makeText(this, getString(R.string.remove), Toast.LENGTH_SHORT).show();
                    icon.setImageResource(R.drawable.ic_favorite_no);
                }else{


                    ContentValues values = new ContentValues();

                    values.put(TITLE,EXTRA_JUDUL);
                    values.put(DESCRIPTION,EXTRA_DESC);
                    values.put(DATE,EXTRA_RILIS);
                    values.put(IMAGE,EXTRA_URL);

                    getContentResolver().insert(CONTENT_URI,values);
                    setResult(101);


                    Toast.makeText(this, getString(R.string.add), Toast.LENGTH_SHORT).show();
                    icon.setImageResource(R.drawable.ic_favorite_yes);
                }
            }catch (Exception e){

            }
            favHelper.close();
        }
    }
}
