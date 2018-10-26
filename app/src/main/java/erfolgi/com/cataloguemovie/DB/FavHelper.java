package erfolgi.com.cataloguemovie.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import erfolgi.com.cataloguemovie.Entity.Favorite;

import static android.provider.BaseColumns._ID;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.DATE;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.DESCRIPTION;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.IMAGE;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.NoteColumns.TITLE;
import static erfolgi.com.cataloguemovie.DB.DatabaseContract.TABLE_FAV;

public class FavHelper {
    private static String DATABASE_TABLE = TABLE_FAV;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavHelper(Context context){
        this.context = context;
    }

    public FavHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Favorite> getDataByName(String kata){
        if (kata == "") {

        }
        Cursor cursor = database.query(TABLE_FAV,null,TITLE+" LIKE ?",new String[]{kata+"%"},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Favorite fav;
        Log.d("->> ", kata);

        if (cursor.getCount()>0) {
            do {
                fav = new Favorite();
                Log.d("->> ", cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                fav.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                fav.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                fav.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                fav.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                fav.setImage(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                arrayList.add(fav);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int count (){
        String countQuery = "SELECT  * FROM "+DATABASE_TABLE;
        Cursor cursor = database.rawQuery(countQuery, null);
        int a = cursor.getCount();
        cursor.close();
        return a;
    }

    public boolean check(String kata){
        //String sql=
        Cursor cursor = database.rawQuery("Select * from "+DATABASE_TABLE+" where "+TITLE+" = '"+kata+"'", null);
        if(cursor.getCount()<=0){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }

    public ArrayList<Favorite> query(){
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        Favorite fav;
        if (cursor.getCount()>0) {
            do {

                fav = new Favorite();
                fav.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                fav.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                fav.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                fav.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                fav.setImage(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));

                arrayList.add(fav);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Favorite fav){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(_ID, fav.getId());
        initialValues.put(TITLE, fav.getTitle());
        initialValues.put(DESCRIPTION, fav.getDescription());
        initialValues.put(DATE, fav.getDate());
        initialValues.put(IMAGE, fav.getImage());
        return database.insert(TABLE_FAV, null, initialValues);
    }

    public int update(Favorite fav){
        ContentValues args = new ContentValues();
        args.put(TITLE, fav.getTitle());
        args.put(DESCRIPTION, fav.getDescription());
        args.put(DATE, fav.getDate());
        args.put(IMAGE, fav.getImage());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + fav.getId() + "'", null);
    }

    public int delete(String judul){
        return database.delete(TABLE_FAV, TITLE + " = '"+judul+"'", null);
    }

    public void droptab(){
        dataBaseHelper.onUpgrade(database,1,1);
    }

    //////////////////////////////////////////////////////////
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,TITLE + " = ?", new String[]{id});
    }
}
