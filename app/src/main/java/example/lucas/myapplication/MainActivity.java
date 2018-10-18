package example.lucas.myapplication;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import example.lucas.myapplication.Adapters.RecyclerViewAdapter;
import example.lucas.myapplication.Asynctask.ImagesToSQLiteAsynctask;
import example.lucas.myapplication.Model.Image;
import example.lucas.myapplication.Provider.ImageListProviderAsyncTask;
import example.lucas.myapplication.SQLiteDatabase.ImagesSQLIiteDatabase;

@Keep
public class MainActivity extends AppCompatActivity implements ImageListProviderAsyncTask.ImageListCallback, ImagesToSQLiteAsynctask.ImageToDatabaseCallback {

    private static final String TAG = "MainActivity.class";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    TextView mLoadingDialog;

    private ProgressBar mLoadingIndicator;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Bitmap> mImageBitmaps = new ArrayList<>();
    private Image mImage = new Image();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = findViewById(R.id.progressBar);
        mLoadingDialog = findViewById(R.id.loadingTitle);

        //Include values in order to avoid fade effect before and after transition for some elements
        getWindow().setEnterTransition(null);
        initImageBitmaps();
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");
        mImage.setmImageId("1");
        mImage.setmImageName("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        startImageDownloads(mImageUrls);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    public void initRecyclerView(ArrayList<Bitmap> mImageBitmaps) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mLoadingDialog.setVisibility(View.INVISIBLE);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(this, mImageBitmaps, mNames);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void startImageDownloads(ArrayList<String> mImageUrls) {
        ImageListProviderAsyncTask imageDownloadAsynctask = new ImageListProviderAsyncTask(this);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        imageDownloadAsynctask.execute(mImageUrls);
    }

    public void saveImagesInDatabase(ArrayList<Bitmap> mBitmaps) {
        byte[] mByteArrayImage = convertBitmapToByteArray(mBitmaps.get(0));
        mImage.setmImageByteArray(mByteArrayImage);

        ImagesSQLIiteDatabase mInstance = ImagesSQLIiteDatabase.getInstance(MainActivity.this);

        ImagesToSQLiteAsynctask mImagesAsynctask = new ImagesToSQLiteAsynctask(mInstance, this);
        mImagesAsynctask.execute(mImage);
    }

    public byte[] convertBitmapToByteArray(Bitmap mBitmap) {
        ByteArrayOutputStream mBAOS = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, mBAOS);
        return mBAOS.toByteArray();
    }

    @Override
    public void getImageBitmapCallback(ArrayList<Bitmap> mBitmaps) {
        mImageBitmaps = mBitmaps;
        initRecyclerView(mImageBitmaps);

        saveImagesInDatabase(mBitmaps);
    }

    @Override
    public void confirmUpdateDatabaseOperation() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setMessage("The images were successfully saved in the database")
                .setCustomTitle(getLayoutInflater().inflate(R.layout.alert_dialog_title, null))
                .setIcon(R.mipmap.ic_android_debug_bridge_black_48dp)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        mBuilder.create().show();

    }
}
