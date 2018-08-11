package erfolgi.com.cataloguemovie.Item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;

public class MovieItems {
    private int id;
    private String judul;
    private String deskripsi;
    private String rilis;
    private String gambar;

    public MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String judul = object.getString("title");
            String deskripsi = object.getString("overview");
            String rilis = object.getString("release_date");
            String gambar = "http://image.tmdb.org/t/p/w185"+object.getString("poster_path");
            //http://image.tmdb.org/t/p/w185/5N20rQURev5CNDcMjHVUZhpoCNC.jpg --> Gambar

            this.id = id;
            this.judul = judul;
            this.deskripsi = deskripsi;
            this.rilis = rilis;
            this.gambar = gambar;
            Log.d("Hello ", "Im here :"+this.judul);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getRilis() {
        return rilis;
    }

    public String getGambar() {
        return gambar;
    }
///////////////////// Load Gambar///////////////////////

}
