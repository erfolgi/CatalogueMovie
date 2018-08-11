package erfolgi.com.cataloguemovie.DB;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAV = "Favorite";
    public static final class NoteColumns implements BaseColumns {
        //title
        public static String TITLE = "title";
        //description
        public static String DESCRIPTION = "description";
        //date
        public static String DATE = "date";
        //image
        public static String IMAGE = "image";

    }
    ////////////////////////////////////////////////////
    public static final String AUTHORITY = "com.erfolgi.cataloguemovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAV)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}

