package shaishav.com.bebetter.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_LESSON = "lessons";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LESSON = "lesson";
    public static final String COLUMN_CATEGORY = "categories";
    public static final String COLUMN_CREATED_AT = "createdAt";
    public static final String COLUMN_SERVER_ID = "server_id";
    public static final String COLUMN_IS_PUBLIC="public";

    public static final String TABLE_USAGE = "usage";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_USAGE="usage";

    private static final String DATABASE_NAME = "lessons.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LESSON + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text not null,"+COLUMN_LESSON+" text not null, "+COLUMN_CREATED_AT+" long not null, "
            +COLUMN_CATEGORY+" text not null, "+COLUMN_SERVER_ID+" text default 'NA', "+COLUMN_IS_PUBLIC+" int default 0);";

    private static final String CREATE_USAGE_STATS = "create table "
            + TABLE_USAGE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DATE
            + " long not null,"+COLUMN_USAGE+" long not null);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(CREATE_USAGE_STATS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if(oldVersion<newVersion){
//            final String ALTER_TBL = "ALTER TABLE "+MySQLiteHelper.TABLE_SONGS+" ADD COLUMN "
//                    +COLUMN_COUNT+" long not null default 0" +
//                    ","+COLUMN_NEXT_COUNT+" long not null default 0,"+COLUMN_STILL_EXISTING+" boolean not null default true;";
//            db.execSQL(ALTER_TBL);
//            //db.execSQL(ORDER_DATABASE_CREATE);
//        }
    }

}
