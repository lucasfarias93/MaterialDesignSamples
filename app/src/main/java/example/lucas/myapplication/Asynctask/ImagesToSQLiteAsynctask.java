package example.lucas.myapplication.Asynctask;

import android.os.AsyncTask;

import example.lucas.myapplication.Model.Image;
import example.lucas.myapplication.SQLiteDatabase.ImagesSQLIiteDatabase;

public class ImagesToSQLiteAsynctask extends AsyncTask<Image, Void, Void> {

    private ImageToDatabaseCallback mCallback;
    private ImagesSQLIiteDatabase mDatabaseInstance;

    public interface ImageToDatabaseCallback {
        void confirmUpdateDatabaseOperation();
    }

    public ImagesToSQLiteAsynctask(ImagesSQLIiteDatabase databaseInstance, ImageToDatabaseCallback callback) {
        mCallback = callback;
        mDatabaseInstance = databaseInstance;
    }

    @Override
    protected Void doInBackground(Image... objects) {
        Image mImage = objects[0];
        mDatabaseInstance.saveImageRecord(mImage);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mCallback.confirmUpdateDatabaseOperation();
    }
}
