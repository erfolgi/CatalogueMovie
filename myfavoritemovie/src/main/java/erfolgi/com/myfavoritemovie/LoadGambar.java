package erfolgi.com.myfavoritemovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class LoadGambar extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public LoadGambar(ImageView imageView) {
        this.imageView = imageView;


    }

    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        Log.d("LINK 2: ", imageURL);
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
