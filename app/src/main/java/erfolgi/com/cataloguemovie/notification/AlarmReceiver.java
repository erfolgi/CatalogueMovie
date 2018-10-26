package erfolgi.com.cataloguemovie.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import erfolgi.com.cataloguemovie.BuildConfig;
import erfolgi.com.cataloguemovie.Item.MovieItems;
import erfolgi.com.cataloguemovie.NavBar;
import erfolgi.com.cataloguemovie.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_ALERT = "NewMovies";//"OneTimeAlarm";
    public static final String TYPE_DAILY = "Daily";//"RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private final int DAILY_ID = 100;
    private final int ALERT_ID = 101;
    private NotificationCompat.Builder notification;
    private Handler handler = new Handler();
    Context con;
    String YMD;
    Calendar AlertTime;
    static int AlertHour;
    static int DailyHour;


    @Override
    public void onReceive(Context context, Intent intent) {

            // Set alarms
            con = context;
            Log.d("<->", "Receive");

            String type = intent.getStringExtra(EXTRA_TYPE);
            int notifId = type.equalsIgnoreCase(TYPE_ALERT) ? ALERT_ID : DAILY_ID; // DAILY_ID : ALERT_ID;
            Log.d("[]_[]", "Type: "+type);

            if(type.equals(TYPE_DAILY)){
                Log.d("<->", "DAILY");
                Log.d("[]_[]", "Executing Daily");
                showDailyNotification(context, notifId);
            }else if (type.equals(TYPE_ALERT)){
                showAlertNotification(context, notifId);
                Log.d("<->", "ALERT");
                Log.d("[]_[]", "Executing Alert");
            }
    }

    private void showDailyNotification(Context context, int notifId){
        Calendar now = Calendar.getInstance();
        int a=now.get(Calendar.HOUR_OF_DAY);

        Log.d("[]_[]", "Daily Now: "+a);
        Log.d("[]_[]", "Daily Target: "+DailyHour);

        if(a>=AlertHour&&a<=(AlertHour+1)){
            //Karena saat Alarm baru di-set biasanya langsung muncul notif meskipun tidak sesuai dengan waktunya.
            //Jadi dibuat if untuk mengakali kondisi tersebut

            con = context;
            Log.d("->->->", "Notif Shown");
            Intent notifyIntent = new Intent(context, NavBar.class);

            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                    context, 0, notifyIntent, FLAG_UPDATE_CURRENT
            );

            NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat
                    .Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory
                            .decodeResource(context.getResources(), R.mipmap.ic_launcher_round))
                    .setContentTitle("Good Morning.")
                    .setContentText("Don't forget to check out movie catalogue")
                    .setAutoCancel(true);
            notification.setContentIntent(notifyPendingIntent);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context.getApplicationContext());
            notificationManagerCompat.notify(DAILY_ID, notification.build());
        }

    }

    public void setReminder(Context context, String type){

        con = context;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        DailyHour=calendar.get(Calendar.HOUR_OF_DAY);


        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, DAILY_ID, intent, FLAG_UPDATE_CURRENT);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("[]_[]", "Reminder is Started");
    }
    public void cancelReminder(Context context){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = DAILY_ID;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Log.d("[]_[]", "Reminder is Cancelled");
    }


    public void setAlert(Context context, String type){
        Log.d("[]_[]", "Alert is Started");
        con = context;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        AlertTime = Calendar.getInstance();
        AlertTime.set(Calendar.HOUR_OF_DAY, 8);
        AlertTime.set(Calendar.MINUTE, 0);
        AlertTime.set(Calendar.SECOND, 0);
        AlertHour=AlertTime.get(Calendar.HOUR_OF_DAY);


        int requestCode = ALERT_ID;
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, requestCode, intent, FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, AlertTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);//AlarmManager.INTERVAL_DAY
        Log.d("[]_[]", "Now: "+AlertTime.getInstance().getTimeInMillis()+" & Milli: "+AlertTime.getTimeInMillis());
    }

    public void cancelAlert(Context context){
        Log.d("[]_[]", "Alert is Cancelled");
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = ALERT_ID;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }


    private void showAlertNotification(Context context, int notifId){


        String D = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        String M = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        String Y = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        if(D.length()<2){
            D = "0"+D;
        }
        if(M.length()<2){
            M = "0"+M;
        }
        YMD= Y+"-"+M+"-"+D;
        Log.d("<->", YMD);

        new LoadData().execute();

        }

        public void alerting(ArrayList<MovieItems> movieItemses){
        Calendar now = Calendar.getInstance();
            int a=now.get(Calendar.HOUR_OF_DAY);
            Log.d("[]_[]", "Alert Now: "+a);
            Log.d("[]_[]", "Alert Target: "+AlertHour);
            if(a>=AlertHour&&a<=(AlertHour+1)){
                //Karena saat Alarm baru di-set biasanya langsung muncul notif meskipun tidak sesuai dengan waktunya.
                //Jadi dibuat if untuk mengakali kondisi tersebut
                String Text;
                if (movieItemses.size()>0){
                    if(movieItemses.size()>1){
                        Text = movieItemses.get(0).getJudul() +" and "+(movieItemses.size()-1)+" more movies are released today!";
                    }else {
                        Text = movieItemses.get(0).getJudul() +"is released today!";
                    }
                    Log.d("<->", Text);

                    Intent notifyIntent = new Intent(con, NavBar.class);

                    notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                            con, 0, notifyIntent, FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat
                            .Builder(con)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Release Today")
                            .setContentText(Text)
                            .setAutoCancel(true);
                    notification.setContentIntent(notifyPendingIntent);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(con.getApplicationContext());
                    notificationManagerCompat.notify(ALERT_ID, notification.build());
                }else {
                    Log.d("<->", "No new movie today, sorry");

                }
            }



        }

    private class LoadData extends AsyncTask<Void, Integer, ArrayList<MovieItems>> {

        final ArrayList<MovieItems> movieItemses = new ArrayList<>();

        @Override
        protected ArrayList<MovieItems> doInBackground(Void... params) {

            SyncHttpClient client = new SyncHttpClient();

            String url ="https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.API_KEY+"&language=en-US";

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

                            if(movieItems.getRilis().contains(YMD)){
                                movieItemses.add(movieItems);
                            }

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

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItems> result) {
            alerting(movieItemses);
        }

    }


}

