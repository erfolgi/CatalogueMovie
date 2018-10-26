package erfolgi.com.cataloguemovie.notification;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import erfolgi.com.cataloguemovie.BuildConfig;
import erfolgi.com.cataloguemovie.Item.MovieItems;
import erfolgi.com.cataloguemovie.Loader.TheAsyncTaskLoader;
import erfolgi.com.cataloguemovie.R;

public class Alert extends Service {
    static String D = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
    static String M = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
    static String Y = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    public Alert() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMin = Calendar.getInstance().get(Calendar.MINUTE);
        int currentSec = Calendar.getInstance().get(Calendar.SECOND);


        if(D.length()<2){
            D = "0"+D;
        }
        if(M.length()<2){
            M = "0"+M;
        }

        //String check = Y+"-"+M+"-"+D;

        if (currentHour==8&&currentMin==0&&currentSec==0){
            ProcessAsync mProcessAsync = new ProcessAsync();
            mProcessAsync.execute();
        }

        Log.d("->", "Sekarang Jam "+currentHour);
        return super.onStartCommand(intent, flags, startId);
    }

    private class ProcessAsync extends AsyncTask<Void, Void, Void> {

        String url="https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.API_KEY+"&language=en-US";
        int time =0;
        @Override
        protected Void doInBackground(Void... params) {


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
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray list = responseObject.getJSONArray("results");

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject film = list.getJSONObject(i);
                            MovieItems movieItems = new MovieItems(film);
                            String ril = movieItems.getRilis();
                            if(ril.contains("-"+M+"-")){
                                time++;
                            }
                            movieItemses.add(movieItems);
                            Log.d("Load: ", "Success");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Jika response gagal maka , do nothing
                    Log.d("->->", "GAGAL");
                }




            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(time>0){

                NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat
                        .Builder(Alert.this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setLargeIcon(BitmapFactory
                                .decodeResource(Alert.this.getResources(), R.mipmap.ic_launcher_round))
                        .setContentTitle("Upcoming Movies")
                        .setContentText("There are "+time+" movies aired this month!")
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Alert.this.getApplicationContext());
                notificationManagerCompat.notify(1001, notification.build());
            }
        }
    }
}
