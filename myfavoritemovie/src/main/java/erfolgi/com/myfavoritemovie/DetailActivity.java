package erfolgi.com.myfavoritemovie;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity  {
    public static String EXTRA_JUDUL="judulnya";
    public static String EXTRA_DESC = "deskripsinya";
    public static String EXTRA_RILIS= "tanggalnya";
    public static String EXTRA_URL= "Gambarnya";




    @BindView(R.id.detail_judul)
    TextView judul;
    @BindView(R.id.detail_deskripsi) TextView desc;
    @BindView(R.id.detail_rilis) TextView rilis;
    @BindView(R.id.detail_gambar)
    ImageView gambar;
    @BindView(R.id.btn_detail)
    Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        EXTRA_JUDUL=getIntent().getStringExtra(EXTRA_JUDUL);
        EXTRA_DESC=getIntent().getStringExtra(EXTRA_DESC);
        EXTRA_RILIS=getIntent().getStringExtra(EXTRA_RILIS);
        EXTRA_URL=getIntent().getStringExtra(EXTRA_URL);
        String NEW_URL = EXTRA_URL.replace("/w185/", "/w780/"); //ganti gambar yang besar
        judul.setText(EXTRA_JUDUL);
        desc.setText(EXTRA_DESC);
        rilis.setText(EXTRA_RILIS);
        new LoadGambar(gambar).execute(NEW_URL); //Tidak bisa menggunakan Glide. Gambar tidak muncul


//        Glide.with(DetailActivity.this). //Tidak muncul
////                load(NEW_URL)
////                .into(gambar);

    }
    @OnClick(R.id.btn_detail)
    public void Submit(View view) {
        Toast.makeText(this, "Download sendiri dong :P", Toast.LENGTH_SHORT).show();

    }

}
