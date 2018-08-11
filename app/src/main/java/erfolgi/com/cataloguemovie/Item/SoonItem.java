package erfolgi.com.cataloguemovie.Item;


import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;

public class SoonItem {
    private int id;
    private String judul;
    private String deskripsi;
    private String rilis;
    private String gambar;

    public SoonItem(JSONObject object) {
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
            Log.d("Hello ", "Im here soon:"+this.judul);
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

    public int getIds() {
        return id;
    }

    public String getJuduls() {
        return judul;
    }

    public String getDeskripsis() {
        return deskripsi;
    }

    public String getRiliss() {
        return rilis;
    }

    public String getGambars() {
        return gambar;
    }
}
