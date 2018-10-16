package example.lucas.myapplication.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ImagesSQLIiteDatabase extends SQLiteOpenHelper{

    private static ImagesSQLIiteDatabase mInstance;

    //Database info
    public static final String DATABASE_NAME = "exampleDatabase.db";
    public static final int DATABASE_VERSION = 1;

    //Tables info
    public static final String TABLE_IMAGES = "images";

    //Images table columns
    public static final String IMAGE_NAMES = "image_names";
    public static final String IMAGE_URLS = "image_path";

    //Queries for creating tables
    private String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_IMAGES +
            "(" +
            KEY_POST_ID + " INTEGER PRIMARY KEY," + // Define a primary key
            KEY_POST_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
            KEY_POST_TEXT + " TEXT" +
            ")";

    public ImagesSQLIiteDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Singleton Pattern for getting the instance
    public static synchronized ImagesSQLIiteDatabase getInstance(Context context) {
        if(mInstance != null){
            mInstance = new ImagesSQLIiteDatabase(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
