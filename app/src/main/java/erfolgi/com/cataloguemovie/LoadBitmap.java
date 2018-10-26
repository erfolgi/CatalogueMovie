package erfolgi.com.cataloguemovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class LoadBitmap {
    public static Bitmap LoadBit( Context con,String... urls ) {
        String imageURL = urls[0];
        Bitmap bimage = null;
        try {
            URL url = new URL(imageURL);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(50000);
            InputStream inp = conn.getInputStream();

            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(inp);
        } catch (Exception e) {
            bimage = BitmapFactory
                    .decodeResource(con.getResources(),R.drawable.gambar);
        }
        return bimage;
    }
}
