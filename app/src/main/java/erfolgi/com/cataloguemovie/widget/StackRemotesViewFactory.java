package erfolgi.com.cataloguemovie.widget;

import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import erfolgi.com.cataloguemovie.Adapter.FavAdapter;
import erfolgi.com.cataloguemovie.BuildConfig;
import erfolgi.com.cataloguemovie.DB.FavHelper;
import erfolgi.com.cataloguemovie.Entity.Favorite;
import erfolgi.com.cataloguemovie.LoadBitmap;
import erfolgi.com.cataloguemovie.LoadGambar;
import erfolgi.com.cataloguemovie.R;

public class StackRemotesViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    FavAdapter favAdapter;
    FavHelper favHelper;

    public StackRemotesViewFactory(Context context, Intent intent) {
        Log.d("->->", "Constructor");
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Log.d("->->", "Factory");
        favHelper = new FavHelper(mContext);
        favAdapter = new FavAdapter(mContext);
        Log.d("->->", "Inizialized");
        favHelper.open();
        Log.d("->->", "Mining");
        final ArrayList<Favorite> fav = favHelper.query();

        Log.d("->->", "GetCount");
        int count=favHelper.count();
        Log.d("->->", "Got :"+count);

        favHelper.close();



        for (int i=0; i<count; i++){
             //Tidak bisa menggunakan Glide. Gambar tidak muncul
//            Log.d("GLIDE", "PRE");
//            mWidgetItems.add(LoadBitmap.LoadBit(mContext,fav.get(i).getImage()));
//            Log.d("GLIDE", "POST");

            Log.d("->->", fav.get(i).getImage());
            final int finalI = i;


            Glide.with(mContext)
                    .asBitmap()
                    .load(fav.get(i).getImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {
                            Log.d("->->", "Gliding "+fav.get(finalI).getImage());

                            Log.d("->->", "Height "+resource.getHeight());
                            Log.d("->->", "Weight "+resource.getWidth());
                            Log.d("->->", "Byte "+resource.getAllocationByteCount());

                            mWidgetItems.add(resource);

                                Log.d("->->", "Added");

                        }
                    });
        }



//        mWidgetItems.add(BitmapFactory
//                .decodeResource(mContext.getResources(),R.drawable.gambar));


    }
    public List<Bitmap> addlist(Context con){

        final List<Bitmap> temp = new ArrayList<>();

        favHelper.open();
        final ArrayList<Favorite> fav = favHelper.query();

        int count=favHelper.count();

        favHelper.close();


        for (int i=0; i<count; i++){

            temp.add(LoadBitmap.LoadBit(con, fav.get(i).getImage()));


        }
    return temp;
    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
           mWidgetItems.addAll(addlist(mContext));
    }



    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.WidgetView,mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(TheWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.WidgetView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
