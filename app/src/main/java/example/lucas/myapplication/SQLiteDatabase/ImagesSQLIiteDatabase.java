package example.lucas.myapplication.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import example.lucas.myapplication.Model.Image;

public class ImagesSQLIiteDatabase extends SQLiteOpenHelper{

    private static final String TAG = "ImagesSQLIiteDatabase.class";

    private static ImagesSQLIiteDatabase mInstance;

    // Database info
    public static final String DATABASE_NAME = "exampleDatabase.db";
    public static final int DATABASE_VERSION = 1;

    // Tables info
    public static final String TABLE_IMAGES = "images";

    // Image table columns
    public static final String KEY_IMAGE_ID = "image_id";
    public static final String KEY_IMAGE_NAME = "image_name";
    public static final String KEY_IMAGE_PATH = "image_path";

    // Queries for creating tables
    private String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_IMAGES +
            "(" +
            KEY_IMAGE_ID + " INTEGER PRIMARY KEY," +
            KEY_IMAGE_NAME + " TEXT " + "," +
            KEY_IMAGE_PATH + " TEXT" +
            ")";

    public ImagesSQLIiteDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton Pattern for getting the instance
    public static synchronized ImagesSQLIiteDatabase getInstance(Context context) {
        if(mInstance == null){
            mInstance = new ImagesSQLIiteDatabase(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_POSTS_TABLE);
    }

    // Called when database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        }
    }

    public void saveImageRecord(Image image) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            // TODO: implement checking for currently saved images in order to avoid duplicate values in DB.
            //long mImageId = checkImageInDatabase(image.mImageId);

            ContentValues mValues = new ContentValues();
            mValues.put(KEY_IMAGE_ID, image.mImageId);
            mValues.put(KEY_IMAGE_NAME, image.mImageName);
            mValues.put(KEY_IMAGE_PATH, image.mImageByteArray);

            db.insertOrThrow(TABLE_IMAGES, null, mValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to insert a record");
        } finally {
            db.endTransaction();
        }
    }
}
