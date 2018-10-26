package erfolgi.com.cataloguemovie.Loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import erfolgi.com.cataloguemovie.Item.MovieItems;

public class TheAsyncTaskLoader extends AsyncTaskLoader {
    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;
    private String mKumpulanFilm;
    //private static final String API_KEY = "bb83bd5cf496014b1bf123c7b88809ad";
    private String url;

    public TheAsyncTaskLoader(final Context context,String url) {
        super(context);
        onContentChanged();
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }


    private void deliverResult(final ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }
    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }
    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemses = new ArrayList<>();


        //https://api.themoviedb.org/3/search/movie?api_key=<API KEY ANDA>&language=en-US&query=<INPUTAN NAMA MOVIE>

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                movieItemses.clear();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.d("<->", Arrays.toString(responseBody));
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject film = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(film);
                        movieItemses.add(movieItems);
                        Log.d("Load: ", "Success");

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
                Log.d("->->", "GAGAL");

            }


        });
        return movieItemses;
    }
    protected void onReleaseResources(ArrayList<MovieItems> data) {
        //nothing to do.
    }

}
