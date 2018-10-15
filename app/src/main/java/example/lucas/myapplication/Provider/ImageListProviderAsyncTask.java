package example.lucas.myapplication.Provider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ImageListProviderAsyncTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Bitmap>> {

    private ImageListCallback callback;

    public interface ImageListCallback{
        void getImageBitmapCallback(ArrayList<Bitmap> bitmap);
    }

    public ImageListProviderAsyncTask(ImageListCallback callback){
        this.callback = callback;
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(ArrayList<String>... arrayLists) {
        ArrayList<Bitmap> mBitmaps = new ArrayList<>();
        for(String imageUrl: arrayLists[0]){
            try {
                InputStream mInputStream = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(mInputStream);
                Bitmap mBitmapScaled = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                mInputStream.close();
                mBitmaps.add(mBitmapScaled);
            } catch (Exception e) {
                Log.e("Exception: ", e.getMessage());
            }
        }
        return mBitmaps;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> mBitmaps) {
        callback.getImageBitmapCallback(mBitmaps);
    }
}
