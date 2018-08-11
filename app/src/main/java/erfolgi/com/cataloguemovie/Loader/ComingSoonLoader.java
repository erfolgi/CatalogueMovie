package erfolgi.com.cataloguemovie.Loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import erfolgi.com.cataloguemovie.Item.SoonItem;

public class ComingSoonLoader extends AsyncTaskLoader {
    private ArrayList<SoonItem> mData;
    private boolean mHasResult = false;
    private String url;

    public ComingSoonLoader(final Context context,String url) {
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

    private void deliverResult(final ArrayList<SoonItem> data) {
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

    @Nullable
    @Override
    public Object loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<SoonItem> soonItemses = new ArrayList<>();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
                soonItemses.clear();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject film = list.getJSONObject(i);
                        SoonItem soonItems = new SoonItem(film);
                        soonItemses.add(soonItems);
                        Log.d("Load: ", "Success");

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
                Log.d("Load: ", "GAGAL");
            }
        });
        return soonItemses;
    }
    protected void onReleaseResources(ArrayList<SoonItem> data) {
        //nothing to do.
    }
}
